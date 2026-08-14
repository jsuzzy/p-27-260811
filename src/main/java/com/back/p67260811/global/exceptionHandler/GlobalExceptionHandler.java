package com.back.p67260811.global.exceptionHandler;

import com.back.p67260811.global.dto.RsData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;

@ControllerAdvice //Controller 안에서 발생하는 예외 한에서 예외를 잡음
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class) //매개변수로 전달된 예외의 처리를 아래 메서드가 처리
    @ResponseBody
    public RsData<Void> handleException(){
        return new RsData<Void>(
                "404-1",
                "존재하지 않는 데이터입니다."
        );
    }

}
