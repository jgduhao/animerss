package jgduhao.github.io.animerss.utils;

import jgduhao.github.io.animerss.vo.PageVo;
import org.springframework.data.domain.Page;

public class PageTransfer<T> {

    private Page<T> page;

    private int pageNum;

    private int pageSize;

    public PageTransfer(Page<T> page, int pageNum, int pageSize) {
        this.page = page;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageVo<T> transfer2Vo(){
        PageVo<T> vo = new PageVo<>();
        vo.setPageData(page.getContent());
        vo.setPageNum(pageNum);
        vo.setPageSize(pageSize);
        vo.setTotalPages(page.getTotalPages());
        vo.setTotalElements(page.getTotalElements());
        vo.setHasNext(page.hasNext());
        vo.setHasPrevious(page.hasPrevious());
        return vo;
    }

}
