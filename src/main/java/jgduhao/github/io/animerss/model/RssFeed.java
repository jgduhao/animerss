package jgduhao.github.io.animerss.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "rss_feed")
public class RssFeed implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rss_feed_id", nullable = false)
    private long rssFeedId;

    @Column(name = "rss_feed_name", length = 200, nullable = false)
    @NotBlank(message = "名称不能为空")
    @Length(min = 1, max = 50, message = "名称长度必须在{min}到{max}字之间")
    private String rssFeedName;

    @Column(name = "rss_feed_url", length = 2000, nullable = false)
    @NotBlank(message = "RSS地址不能为空")
    @Length(min = 1, max = 500, message = "RSS地址长度必须在{min}到{max}之间")
    private String rssFeedUrl;

    @Column(name = "rss_feed_create_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date rssFeedCreateDate;

    @Column(name = "rss_feed_update_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date rssFeedUpdateDate;

    @Column(name = "categoryId", nullable = false)
    private long categoryId;

    public RssFeed() {
    }

    public long getRssFeedId() {
        return rssFeedId;
    }

    public void setRssFeedId(long rssFeedId) {
        this.rssFeedId = rssFeedId;
    }

    public String getRssFeedName() {
        return rssFeedName;
    }

    public void setRssFeedName(String rssFeedName) {
        this.rssFeedName = rssFeedName;
    }

    public String getRssFeedUrl() {
        return rssFeedUrl;
    }

    public void setRssFeedUrl(String rssFeedUrl) {
        this.rssFeedUrl = rssFeedUrl;
    }

    public Date getRssFeedCreateDate() {
        return rssFeedCreateDate;
    }

    public void setRssFeedCreateDate(Date rssFeedCreateDate) {
        this.rssFeedCreateDate = rssFeedCreateDate;
    }

    public Date getRssFeedUpdateDate() {
        return rssFeedUpdateDate;
    }

    public void setRssFeedUpdateDate(Date rssFeedUpdateDate) {
        this.rssFeedUpdateDate = rssFeedUpdateDate;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "RssFeed{" +
                "rssFeedId=" + rssFeedId +
                ", rssFeedName='" + rssFeedName + '\'' +
                ", rssFeedUrl='" + rssFeedUrl + '\'' +
                ", rssFeedCreateDate=" + rssFeedCreateDate +
                ", rssFeedUpdateDate=" + rssFeedUpdateDate +
                ", categoryId=" + categoryId +
                '}';
    }
}
