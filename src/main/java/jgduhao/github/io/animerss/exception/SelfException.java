package jgduhao.github.io.animerss.exception;

import jgduhao.github.io.animerss.enums.ErrorMsg;
import org.springframework.http.HttpStatus;

public class SelfException extends RuntimeException{

    private ErrorMsg errorMsg;

    public SelfException() {
        super();
    }

    public SelfException(ErrorMsg errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg.getErrorMsg();
    }

    public HttpStatus getErrorStatus(){
        return errorMsg.getHttpStatus();
    }


}
