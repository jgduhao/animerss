package jgduhao.github.io.animerss.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id", nullable = false)
    private long categoryId;

    @Column(name = "category_name", length = 200, nullable = false)
    private String categoryName;

    @Column(name = "category_visible", nullable = false)
    private int categoryVisible;

    @Column(name = "category_create_date", nullable = false)
    private Date categoryCreateDate;

    @Column(name = "category_update_date")
    private Date categoryUpdateDate;

    public Category() {
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryVisible() {
        return categoryVisible;
    }

    public void setCategoryVisible(int categoryVisible) {
        this.categoryVisible = categoryVisible;
    }

    public Date getCategoryCreateDate() {
        return categoryCreateDate;
    }

    public void setCategoryCreateDate(Date categoryCreateDate) {
        this.categoryCreateDate = categoryCreateDate;
    }

    public Date getCategoryUpdateDate() {
        return categoryUpdateDate;
    }

    public void setCategoryUpdateDate(Date categoryUpdateDate) {
        this.categoryUpdateDate = categoryUpdateDate;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryVisible=" + categoryVisible +
                ", categoryCreateDate=" + categoryCreateDate +
                ", categoryUpdateDate=" + categoryUpdateDate +
                '}';
    }
}
