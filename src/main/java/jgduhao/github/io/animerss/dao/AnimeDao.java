package jgduhao.github.io.animerss.dao;

import jgduhao.github.io.animerss.model.Anime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeDao extends JpaRepository<Anime, Long> {

    List<Anime> findByRssFeedId(long rssFeedId);

    Page<Anime> findByRssFeedId(long rssFeedId, Pageable pageable);

    void deleteByRssFeedId(long rssFeedId);

}
