package jgduhao.github.io.animerss.service.impl;

import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import jgduhao.github.io.animerss.enums.Aria2RpcMethod;
import jgduhao.github.io.animerss.enums.Consts;
import jgduhao.github.io.animerss.enums.ErrorMsg;
import jgduhao.github.io.animerss.exception.SelfException;
import jgduhao.github.io.animerss.model.Anime;
import jgduhao.github.io.animerss.service.CoreService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoreServiceImpl implements CoreService {

    private Log log = LogFactory.getLog(CoreServiceImpl.class);

    @Value("${animerss.proxy.host}")
    private String proxyHost;
    @Value("${animerss.proxy.port}")
    private String proxyPort;

    @Value("${animerss.aria2.secret}")
    private String aria2secret;
    @Value("${animerss.aria2.rpcurl}")
    private String aria2url;

    @Override
    public List<Anime> parseRss2AnimeList(String rssUrl){
        List<Anime> animeList = new ArrayList<>();
        try{
            URL url = new URL(rssUrl);
            XmlReader xmlReader = new XmlReader(getInputStream(url));
            SyndFeedInput syndFeedInput = new SyndFeedInput();
            SyndFeed syndFeed = syndFeedInput.build(xmlReader);
            List<SyndEntry> syndEntryList = syndFeed.getEntries();
            for(SyndEntry syndEntry : syndEntryList){
                Anime anime = new Anime();
                anime.setAnimeTitle(syndEntry.getTitle());
                anime.setAnimePublishDate(syndEntry.getPublishedDate());
                anime.setAnimePageUrl(syndEntry.getUri());
                List<SyndEnclosure> syndEnclosures = syndEntry.getEnclosures();
                if(syndEnclosures != null && syndEnclosures.size() > 0){
                    List<String> downloadUrls = new ArrayList<>();
                    for(SyndEnclosure syndEnclosure : syndEnclosures){
                        String downloadUrl = syndEnclosure.getUrl();
                        if(downloadUrl != null && !"".equals(downloadUrl)){
                            downloadUrls.add(downloadUrl);
                        }
                    }
                    if(downloadUrls.size() > 0){
                        anime.setDownloadUrl(downloadUrls.get(0));
                    }
                }
                animeList.add(anime);
            }
        } catch (IOException e){
            log.error(e.getMessage(), e);
            throw new SelfException(ErrorMsg.RssFeedGetError);
        } catch (FeedException e){
            log.error(e.getMessage(), e);
            throw new SelfException(ErrorMsg.RssFeedParseError);
        }
        return animeList;
    }

    @Override
    public void sendDownload2Aria2(String downloadUrl){
        if(checkIsTorrent(downloadUrl)){
            Aria2RpcMethod method = Aria2RpcMethod.addTorrent;
            byte[] bytes = torrentTransfer2Bytes(downloadUrl);
            if(hasSecret()){
                executeRpc(method, new Object[]{getSecret(), bytes});
            } else {
                executeRpc(method, new Object[]{bytes});
            }
        } else if(checkIsMagnet(downloadUrl)){
            Aria2RpcMethod method = Aria2RpcMethod.addUri;
            if(hasSecret()){
                executeRpc(method, new Object[]{getSecret(), new String[]{downloadUrl}});
            } else {
                executeRpc(method, new Object[]{new String[]{downloadUrl}});
            }
        } else {
            throw new SelfException(ErrorMsg.AnimeUnsupportedUrl);
        }
    }

    private void executeRpc(Aria2RpcMethod method, Object params[]){
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        String sendUrl = aria2url;
        if(!sendUrl.endsWith("/")){
            sendUrl += "/";
        }
        try{
            config.setServerURL(new URL(sendUrl + "rpc"));
            config.setEnabledForExtensions(true);
            config.setEnabledForExceptions(true);
            config.setConnectionTimeout(10 * 1000);
            config.setReplyTimeout(10 * 1000);
            XmlRpcClient client = new XmlRpcClient();
            client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
            client.setConfig(config);
            client.execute(method.getMethodName(), params);
        } catch (MalformedURLException e){
            log.error(e.getMessage(), e);
            throw new SelfException(ErrorMsg.Aria2UrlError);
        } catch (XmlRpcException e){
            log.error(e.getMessage(), e);
            throw new SelfException(ErrorMsg.Aria2RpcError);
        }
    }

    private String getSecret(){
        return "token:" + aria2secret;
    }

    private boolean hasSecret(){
        return aria2secret != null && !"".equals(aria2secret);
    }

    private boolean checkIsTorrent(String downloadUrl){
        return downloadUrl != null && downloadUrl.toLowerCase().endsWith(".torrent");
    }

    private boolean checkIsMagnet(String downloadUrl){
        return downloadUrl != null && downloadUrl.toLowerCase().startsWith("magnet:");
    }

    public byte[] torrentTransfer2Bytes(String torrentUrl) {
        torrentUrl = encodeTorrentName(torrentUrl);
        try{
            URL url = new URL(torrentUrl);
            return IOUtils.toByteArray(getInputStream(url));
        } catch (IOException e){
            log.error(e.getMessage(), e);
            throw new SelfException(ErrorMsg.TorrentDownloadError);
        }
    }

    private String encodeTorrentName(String torrentUrl){
        String torrentName = torrentUrl.substring(torrentUrl.lastIndexOf("/") + 1);
        String torrentUrlPrefix = torrentUrl.substring(0, torrentUrl.lastIndexOf("/") + 1);
        try {
            torrentName = URLEncoder.encode(torrentName, "UTF-8");
            torrentUrl = torrentUrlPrefix + torrentName;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return torrentUrl;
    }


    private InputStream getInputStream(URL url){
        HttpURLConnection httpConn;
        try {
            if(proxyHost != null && !"".equals(proxyHost) && proxyPort != null && !"".equals(proxyPort)){
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, Integer.parseInt(proxyPort)));
                httpConn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                httpConn = (HttpURLConnection) url.openConnection();
            }
            httpConn.setRequestProperty("accept", "*/*");
            httpConn.setRequestProperty("connection", "Keep-Alive");
            httpConn.setRequestProperty("user-agent", Consts.USER_AGENT);
            return httpConn.getInputStream();
        } catch (IOException e){
            log.error(e.getMessage(), e);
            throw new SelfException(ErrorMsg.NetConnectError);
        }
    }

}
