package com.ende.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

//@ControllerAdvice
public class ExceptionProcess {
    // 对这个异常的统一处理，返回值 和Controller的返回规则一样
    @ExceptionHandler(MultipartException.class)
    public String handleAll(Throwable t){
    	System.out.println("dddddddddddddddddddddddddddd");
        return "error";
    }
}