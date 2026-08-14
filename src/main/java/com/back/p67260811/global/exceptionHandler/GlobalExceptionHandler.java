package com.back.p67260811.global.exceptionHandler;

import com.back.p67260811.global.dto.RsData;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice //Controller 안에서 발생하는 예외 한에서 예외를 잡음
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class) //매개변수로 전달된 예외의 처리를 아래 메서드가 처리
    @ResponseBody
    public RsData<Void> noSuchElementException(){
        return new RsData<Void>(
                "404-1",
                "존재하지 않는 데이터입니다."
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //매개변수로 전달된 예외의 처리를 아래 메서드가 처리
    @ResponseBody
    public RsData<Void> methodArgumentNotValidException(MethodArgumentNotValidException e){

        String message = e.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .map(error -> error.getField() + "-" + error.getCode() + "-" + error.getDefaultMessage())
                .sorted(Comparator.comparing(String::toString))
                .collect(Collectors.joining("\n"));

        return new RsData<Void>(
                "400-1",
                message
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class) //매개변수로 전달된 예외의 처리를 아래 메서드가 처리
    @ResponseBody
    public RsData<Void> httpMessageNotReadableException(HttpMessageNotReadableException e){

        return new RsData<Void>(
                "400-2",
                "잘못된 형식의 요청 데이터입니다."
        );
    }
}
