package jgduhao.github.io.animerss.controller;


import jgduhao.github.io.animerss.enums.ErrorMsg;
import jgduhao.github.io.animerss.exception.SelfException;
import jgduhao.github.io.animerss.vo.MessageVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = SelfException.class)
    public ResponseEntity<MessageVo> selfExceptionHandler(SelfException e){
        return ResponseEntity.status(e.getErrorStatus())
                .body(new MessageVo(e.getErrorMsg()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<MessageVo> validationExceptionHandler(MethodArgumentNotValidException e){
        StringBuilder message = new StringBuilder();
        BindingResult result = e.getBindingResult();
        if(result.hasErrors()){
            result.getAllErrors().forEach(p -> {
                FieldError fieldError = (FieldError) p;
                message.append(fieldError.getDefaultMessage()).append(",");
            });
        }
        return ResponseEntity.badRequest()
                .body(new MessageVo(message.substring(0, message.length() - 1)));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<MessageVo> exceptionHandler(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageVo(ErrorMsg.InnerError));
    }

}
