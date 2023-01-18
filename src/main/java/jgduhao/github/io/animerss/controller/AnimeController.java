package jgduhao.github.io.animerss.controller;

import jgduhao.github.io.animerss.model.Anime;
import jgduhao.github.io.animerss.service.AnimeService;
import jgduhao.github.io.animerss.utils.PageTransfer;
import jgduhao.github.io.animerss.vo.MessageVo;
import jgduhao.github.io.animerss.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class AnimeController {

    @Autowired
    AnimeService animeService;

    @Value("${animerss.aria2.rpcurl}")
    private String aria2url;

    @GetMapping("/anime")
    public ResponseEntity<PageVo<Anime>> anime(int pageSize, int pageNum){
        Page<Anime> animePage = animeService.getAnimeByPage(pageSize, pageNum);
        PageVo<Anime> vo = new PageTransfer<>(animePage, pageNum, pageSize).transfer2Vo();
        return ResponseEntity.ok(vo);
    }

    @GetMapping("/rssFeeds/{rssFeedId}/anime")
    public ResponseEntity<PageVo<Anime>> anime(@PathVariable("rssFeedId") long rssFeedId, int pageSize, int pageNum){
        Page<Anime> animePage = animeService.getAnimeByPage(pageSize, pageNum, rssFeedId);
        PageVo<Anime> vo = new PageTransfer<>(animePage, pageNum, pageSize).transfer2Vo();
        return ResponseEntity.ok(vo);
    }

    @PutMapping(value = "/anime/{animeId}/aria2DownloadMission")
    public ResponseEntity<MessageVo> newAria2Download(@PathVariable("animeId") long animeId){
        animeService.send2Aria2Download(animeId);
        return ResponseEntity.created(URI.create(aria2url)).body(new MessageVo());
    }


}
