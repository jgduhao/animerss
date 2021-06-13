package jgduhao.github.io.animerss.service;

import jgduhao.github.io.animerss.model.RssFeed;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RssFeedService {

    List<RssFeed> getAllRssFeedList();

    Page<RssFeed> getRssFeedByPage(int pageSize, int pageNum);

    Optional<RssFeed> getOneRssFeedById(long rssFeedId);

    RssFeed saveRssFeed(RssFeed rssFeed);

    void deleteRssFeed(long rssFeedId);

    int refreshRssFeed(long rssFeedId);

    int refreshAllRssFeed();

    void deleteRssFeedAnimeById(long rssFeedId);

}
