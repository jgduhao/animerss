package jgduhao.github.io.animerss.service.impl;

import jgduhao.github.io.animerss.dao.AnimeDao;
import jgduhao.github.io.animerss.enums.ErrorMsg;
import jgduhao.github.io.animerss.exception.SelfException;
import jgduhao.github.io.animerss.model.Anime;
import jgduhao.github.io.animerss.service.AnimeService;
import jgduhao.github.io.animerss.service.CoreService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AnimeServiceImpl implements AnimeService {

    private Log log = LogFactory.getLog(AnimeServiceImpl.class);

    @Autowired
    private CoreService coreService;

    @Autowired
    private AnimeDao animeDao;

    @Override
    public Page<Anime> getAnimeByPage(int pageSize, int pageNum) {
        return animeDao.findAll(PageRequest.of(pageNum-1, pageSize,
                Sort.by(Sort.Order.desc("animePublishDate"))));
    }

    @Override
    public Page<Anime> getAnimeByPage(int pageSize, int pageNum, long rssFeedId) {
        return animeDao.findByRssFeedId(rssFeedId,
                PageRequest.of(pageNum-1, pageSize, Sort.by(Sort.Order.desc("animePublishDate"))));
    }

    @Override
    public void send2Aria2Download(long animeId) {
        Anime anime = animeDao.findById(animeId)
                .orElseThrow(() -> new SelfException(ErrorMsg.AnimeNotExists));
        String downloadUrl = anime.getDownloadUrl();
        if(downloadUrl == null || "".equals(downloadUrl)){
            throw new SelfException(ErrorMsg.AnimeDownloadUrlNotExists);
        }
        coreService.sendDownload2Aria2(downloadUrl);
    }
}
