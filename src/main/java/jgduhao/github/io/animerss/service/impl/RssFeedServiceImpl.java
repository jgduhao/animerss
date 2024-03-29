package jgduhao.github.io.animerss.service.impl;

import jgduhao.github.io.animerss.dao.AnimeDao;
import jgduhao.github.io.animerss.dao.RssFeedDao;
import jgduhao.github.io.animerss.enums.Consts;
import jgduhao.github.io.animerss.enums.ErrorMsg;
import jgduhao.github.io.animerss.exception.SelfException;
import jgduhao.github.io.animerss.model.Anime;
import jgduhao.github.io.animerss.model.RssFeed;
import jgduhao.github.io.animerss.service.CoreService;
import jgduhao.github.io.animerss.service.RssFeedService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class RssFeedServiceImpl implements RssFeedService {

    private Log log = LogFactory.getLog(RssFeedServiceImpl.class);

    @Autowired
    private CoreService coreService;

    @Autowired
    private RssFeedDao rssFeedDao;

    @Autowired
    private AnimeDao animeDao;

    @Autowired
    private ThreadPoolTaskExecutor poolTaskExecutor;

    @Override
    public List<RssFeed> getAllRssFeedList() {
        return rssFeedDao.findAll(Sort.by(Sort.Order.desc("rssFeedUpdateDate")));
    }

    @Override
    public Page<RssFeed> getRssFeedByPage(int pageSize, int pageNum) {
        return rssFeedDao.findAll(PageRequest.of(pageNum-1, pageSize, Sort.by(Sort.Order.desc("rssFeedUpdateDate"))));
    }

    @Override
    public Optional<RssFeed> getOneRssFeedById(long rssFeedId) {
        return rssFeedDao.findById(rssFeedId);
    }

    @Override
    public RssFeed saveRssFeed(RssFeed rssFeed) {
        return rssFeedDao.save(rssFeed);
    }

    @Override
    public void deleteRssFeed(long rssFeedId) {
        rssFeedDao.deleteById(rssFeedId);
        animeDao.deleteByRssFeedId(rssFeedId);
    }

    @Override
    public void deleteRssFeedAnimeById(long rssFeedId) {
        animeDao.deleteByRssFeedId(rssFeedId);
    }

    @Override
    public int refreshRssFeed(long rssFeedId) {
        RssFeed rssFeed = rssFeedDao.findById(rssFeedId)
                .orElseThrow(() -> new SelfException(ErrorMsg.RssFeedNotExists));
        List<Anime> animeList = coreService.parseRss2AnimeList(rssFeed.getRssFeedUrl());
        List<Anime> dbAnimeList = animeDao.findByRssFeedId(rssFeedId);
        int newCount = 0;
        for(Anime anime : animeList){
            anime.setRssFeedId(rssFeedId);
            anime.setCategoryId(Consts.DEFAULT_CATEGORY_ID);
            if(dbAnimeList == null || !dbAnimeList.contains(anime)){
                log.info(anime);
                animeDao.save(anime);
                newCount++;
            }
        }
        if(newCount > 0){
            rssFeed.setRssFeedUpdateDate(new Date());
            rssFeedDao.save(rssFeed);
        }
        return newCount;
    }

    @Override
    public int refreshAllRssFeed() {
        List<RssFeed> rssFeeds = getAllRssFeedList();
        int newCount = 0;
        for(RssFeed rssFeed : rssFeeds){
            log.info("更新RSS:"+rssFeed.getRssFeedName());
            try{
                newCount += refreshRssFeed(rssFeed.getRssFeedId());
                log.info("更新成功");
            } catch (Exception e){
                log.info("更新失败");
                log.error(e.getMessage(), e);
            }
        }
        return newCount;
    }

    @Override
    public int refreshAllRssFeedWithMultiThread(){
        List<RssFeed> rssFeeds = getAllRssFeedList();
        AtomicInteger newCount = new AtomicInteger();
        if(rssFeeds != null && rssFeeds.size() > 0){
            final CountDownLatch countDownLatch = new CountDownLatch(rssFeeds.size());
            for(RssFeed rssFeed : rssFeeds){
                poolTaskExecutor.execute(() -> {
                    String threadName = Thread.currentThread().getName();
                    log.info(threadName + " 正在更新： " + rssFeed.getRssFeedName());
                    try {
                        int resultCount = refreshRssFeed(rssFeed.getRssFeedId());
                        newCount.getAndAdd(resultCount);
                    } catch (Exception e){
                        log.info(rssFeed.getRssFeedName() + "更新失败");
                        log.error(e.getMessage(), e);
                    }
                    countDownLatch.countDown();
                });
            }
            try {
                countDownLatch.await(120, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.info(e.getMessage(), e);
            }
        }
        log.info("更新完成，共更新" + newCount.get() +"条记录");
        return newCount.get();
    }
}
