package jgduhao.github.io.animerss.service;

import jgduhao.github.io.animerss.model.Anime;

import java.util.List;

public interface CoreService {

    List<Anime> parseRss2AnimeList(String rssUrl);

    void sendDownload2Aria2(String downloadUrl);

}
