package jgduhao.github.io.animerss.service;

import jgduhao.github.io.animerss.model.Anime;
import org.springframework.data.domain.Page;

public interface AnimeService {

    Page<Anime> getAnimeByPage(int pageSize, int pageNum);

    Page<Anime> getAnimeByPage(int pageSize, int pageNum, long rssFeedId);

    void send2Aria2Download(long animeId);

}
