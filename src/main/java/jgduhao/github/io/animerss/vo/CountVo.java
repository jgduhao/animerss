package jgduhao.github.io.animerss.vo;

import java.io.Serializable;

public class CountVo implements Serializable {

    private int newCount;

    public CountVo(int newCount) {
        this.newCount = newCount;
    }

    public CountVo() {
    }

    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

    @Override
    public String toString() {
        return "CountVo{" +
                "newCount=" + newCount +
                '}';
    }
}
