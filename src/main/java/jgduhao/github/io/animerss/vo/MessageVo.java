package jgduhao.github.io.animerss.vo;

import jgduhao.github.io.animerss.enums.ErrorMsg;

import java.io.Serializable;

public class MessageVo implements Serializable {

    private String message;

    public MessageVo() {
        this.message = ErrorMsg.Success.getErrorMsg();
    }

    public MessageVo(ErrorMsg errorMsg) {
        this.message = errorMsg.getErrorMsg();
    }

    public MessageVo(String str){
        this.message = str;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
