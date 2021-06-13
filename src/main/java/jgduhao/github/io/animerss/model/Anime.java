package jgduhao.github.io.animerss.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "anime")
public class Anime implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "anime_id", nullable = false)
    private long animeId;

    @Column(name = "anime_title", length = 800, nullable = false)
    private String animeTitle;

    @Column(name = "anime_publish_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date animePublishDate;

    @Column(name = "download_url", length = 4000)
    private String downloadUrl;

    @Column(name = "anime_page_url", length = 800)
    private String animePageUrl;

    @Column(name = "rss_feed_id", nullable = false)
    private long rssFeedId;

    @Column(name = "category_id", nullable = false)
    private long categoryId;

    public Anime() {
    }

    public long getAnimeId() {
        return animeId;
    }

    public void setAnimeId(long animeId) {
        this.animeId = animeId;
    }

    public String getAnimeTitle() {
        return animeTitle;
    }

    public void setAnimeTitle(String animeTitle) {
        this.animeTitle = animeTitle;
    }

    public Date getAnimePublishDate() {
        return animePublishDate;
    }

    public void setAnimePublishDate(Date animePublishDate) {
        this.animePublishDate = animePublishDate;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public long getRssFeedId() {
        return rssFeedId;
    }

    public void setRssFeedId(long rssFeedId) {
        this.rssFeedId = rssFeedId;
    }

    public String getAnimePageUrl() {
        return animePageUrl;
    }

    public void setAnimePageUrl(String animePageUrl) {
        this.animePageUrl = animePageUrl;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anime anime = (Anime) o;
        return rssFeedId == anime.rssFeedId && categoryId == anime.categoryId && animeTitle.equals(anime.animeTitle) && animePublishDate.equals(anime.animePublishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animeTitle, animePublishDate, rssFeedId, categoryId);
    }

    @Override
    public String toString() {
        return "Anime{" +
                "animeId=" + animeId +
                ", animeTitle='" + animeTitle + '\'' +
                ", animePublishDate=" + animePublishDate +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", animePageUrl='" + animePageUrl + '\'' +
                ", rssFeedId=" + rssFeedId +
                ", categoryId=" + categoryId +
                '}';
    }
}
