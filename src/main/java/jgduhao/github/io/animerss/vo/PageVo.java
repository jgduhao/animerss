package jgduhao.github.io.animerss.vo;

import java.io.Serializable;
import java.util.List;

public class PageVo<T> implements Serializable {

    private List<T> pageData;

    private int totalPages;

    private long totalElements;

    private boolean hasPrevious;

    private boolean hasNext;

    private int pageNum;

    private int pageSize;

    public PageVo() {
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageVo{" +
                "pageData=" + pageData +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", hasPrevious=" + hasPrevious +
                ", hasNext=" + hasNext +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
