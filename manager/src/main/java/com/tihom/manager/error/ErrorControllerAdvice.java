package com.tihom.manager.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TiHom
 * create at 2018/8/2 0002.
 */

@ControllerAdvice(basePackages = "com.tihom.manager.controller")
public class ErrorControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleException(Exception e){
        Map<String,Object> attrs = new HashMap<>();
        //通过返回的message拿到错误对象
        String errorCode = e.getMessage();
        ErrorEnum errorEnum = ErrorEnum.getByCode(errorCode);
        //放入返回参数中
        attrs.put("message",errorEnum.getMessage());
        attrs.put("code",errorEnum.getCode());
        attrs.put("canRetry",errorEnum.isCanRetry());
        attrs.put("type","advice");
        return new ResponseEntity(attrs,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
