package jgduhao.github.io.animerss.service.impl;

import jgduhao.github.io.animerss.enums.Consts;
import jgduhao.github.io.animerss.enums.ErrorMsg;
import jgduhao.github.io.animerss.exception.SelfException;
import jgduhao.github.io.animerss.model.RssFeed;
import jgduhao.github.io.animerss.service.CategoryService;
import jgduhao.github.io.animerss.service.RssFeedService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RssFeedServiceImplTest {

    @Autowired
    RssFeedService rssFeedService;

    @Test
    void getAllRssFeedList() {
        List<RssFeed> list = rssFeedService.getAllRssFeedList();
        System.out.println(list);
    }

    @Test
    void getRssFeedByPage() {
        Page<RssFeed> page = rssFeedService.getRssFeedByPage(10, 1);
        System.out.println(page.toList());
    }

    @Test
    void getOneRssFeedById() {
        RssFeed rssFeed = rssFeedService.getOneRssFeedById(8L).orElseThrow(() -> new SelfException(ErrorMsg.RssFeedNotExists));
        System.out.println(rssFeed);
    }

    @Test
    void saveRssFeed() {
        RssFeed feed = new RssFeed();
        feed.setCategoryId(Consts.DEFAULT_CATEGORY_ID);
        feed.setRssFeedCreateDate(new Date());
        feed.setRssFeedUpdateDate(new Date());
//        feed.setRssFeedName("圣女魔力无所不能");
        feed.setRssFeedName("天空侵犯");
//        feed.setRssFeedUrl("https://bangumi.moe/rss/tags/6065f094eaf504d0216b7793+548ee0ea4ab7379536f56354+58a9c1e6f5dc363606ab42ed+548ee2ce4ab7379536f56358");
        feed.setRssFeedUrl("https://dmhy.anoneko.com/topics/rss/rss.xml?keyword=%E5%A4%A9%E7%A9%BA%E4%BE%B5%E7%8A%AF+%E7%AE%80%E4%BD%93+1080&sort_id=0&team_id=669&order=date-desc");
        rssFeedService.saveRssFeed(feed);
    }

    @Test
    void deleteRssFeed() {
        rssFeedService.deleteRssFeed(58);
    }

    @Test
    void refreshRssFeed() {
        int newCount = rssFeedService.refreshRssFeed(8);
        System.out.println(newCount);
    }

    @Test
    void refreshAllRssFeed() {
        int newCount = rssFeedService.refreshAllRssFeed();
        System.out.println(newCount);
    }
}