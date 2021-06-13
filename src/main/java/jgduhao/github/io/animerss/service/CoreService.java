package jgduhao.github.io.animerss.service;

import com.rometools.rome.io.FeedException;
import jgduhao.github.io.animerss.model.Anime;
import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;
import java.util.List;

public interface CoreService {

    List<Anime> parseRss2AnimeList(String rssUrl);

    void sendDownload2Aria2(String downloadUrl);

}
