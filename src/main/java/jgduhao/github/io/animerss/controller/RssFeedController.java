package jgduhao.github.io.animerss.controller;

import jgduhao.github.io.animerss.enums.Consts;
import jgduhao.github.io.animerss.enums.ErrorMsg;
import jgduhao.github.io.animerss.exception.SelfException;
import jgduhao.github.io.animerss.model.RssFeed;
import jgduhao.github.io.animerss.service.RssFeedService;
import jgduhao.github.io.animerss.utils.PageTransfer;
import jgduhao.github.io.animerss.vo.CountVo;
import jgduhao.github.io.animerss.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
public class RssFeedController {

    @Autowired
    RssFeedService rssFeedService;

    @GetMapping("/rssFeeds/all")
    public ResponseEntity<List<RssFeed>> rssFeeds(){
        List<RssFeed> rssFeeds = rssFeedService.getAllRssFeedList();
        return ResponseEntity.ok(rssFeeds);
    }

    @GetMapping("/rssFeeds")
    public ResponseEntity<PageVo<RssFeed>> rssFeeds(int pageSize, int pageNum){
        Page<RssFeed> rssFeedPage = rssFeedService.getRssFeedByPage(pageSize, pageNum);
        PageVo<RssFeed> pageVo = new PageTransfer<>(rssFeedPage, pageNum, pageSize).transfer2Vo();
        return ResponseEntity.ok(pageVo);
    }

    @GetMapping("/rssFeeds/{rssFeedId}")
    public ResponseEntity<RssFeed> rssFeeds(@PathVariable("rssFeedId") long rssFeedId){
        RssFeed rssFeed = rssFeedService.getOneRssFeedById(rssFeedId)
                .orElseThrow(() -> new SelfException(ErrorMsg.RssFeedNotExists));
        return ResponseEntity.ok(rssFeed);
    }

    @PutMapping("/rssFeeds")
    public ResponseEntity<RssFeed> createRssFeed(@Validated @RequestBody RssFeed rssFeed){
        Date now = new Date();
        rssFeed.setRssFeedCreateDate(now);
        rssFeed.setRssFeedUpdateDate(now);
        rssFeed.setCategoryId(Consts.DEFAULT_CATEGORY_ID);
        RssFeed newRssFeed = rssFeedService.saveRssFeed(rssFeed);
        return ResponseEntity.created(URI.create("/rssFeeds/"+newRssFeed.getRssFeedId()))
                .body(newRssFeed);
    }

    @PatchMapping("/rssFeeds")
    public ResponseEntity<RssFeed> updateRssFeed(@Valid @RequestBody RssFeed rssFeed){
        rssFeedService.getOneRssFeedById(rssFeed.getRssFeedId())
                .orElseThrow(() -> new SelfException(ErrorMsg.RssFeedNotExists));
        RssFeed newRssFeed = rssFeedService.saveRssFeed(rssFeed);
        return ResponseEntity.created(URI.create("/rssFeeds/"+newRssFeed.getRssFeedId()))
                .body(newRssFeed);
    }

    @DeleteMapping("/rssFeeds/{rssFeedId}")
    public ResponseEntity<Object> deleteRssFeed(@PathVariable("rssFeedId") long rssFeedId){
        rssFeedService.deleteRssFeed(rssFeedId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/rssFeeds/{rssFeedId}/anime")
    public ResponseEntity<Void> deleteAnimeByRssFeedId(@PathVariable("rssFeedId") long rssFeedId){
        rssFeedService.deleteRssFeedAnimeById(rssFeedId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/rssFeeds/{rssFeedId}/anime")
    public ResponseEntity<CountVo> refreshRssFeed(@PathVariable("rssFeedId") long rssFeedId){
        int newCount = rssFeedService.refreshRssFeed(rssFeedId);
        return ResponseEntity.created(URI.create("/rssFeeds/"+rssFeedId+"/anime"))
                .body(new CountVo(newCount));
    }

    @PatchMapping("/anime")
    public ResponseEntity<CountVo> refreshRssFeed(){
        int newCount = rssFeedService.refreshAllRssFeedWithMultiThread();
        return ResponseEntity.created(URI.create("/anime"))
                .body(new CountVo(newCount));
    }




}
