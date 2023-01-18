package jgduhao.github.io.animerss.service.impl;

import jgduhao.github.io.animerss.model.Anime;
import jgduhao.github.io.animerss.service.AnimeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class AnimeServiceImplTest {

    @Autowired
    AnimeService animeService;

    @Test
    void getAnimeByPage() {
       Page<Anime> animePage =  animeService.getAnimeByPage(5,1);
       System.out.println(animePage.getContent());
    }

    @Test
    void testGetAnimeByPage() {
        Page<Anime> animePage = animeService.getAnimeByPage(5,1,68);
        System.out.println(animePage.getContent());
        System.out.println(animePage.getTotalPages());
        System.out.println(animePage.getTotalElements());
    }

    @Test
    void send2Aria2Download() {
        animeService.send2Aria2Download(52);
    }
}