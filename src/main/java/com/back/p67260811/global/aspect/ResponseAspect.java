package com.back.p67260811.global.aspect;

import com.back.p67260811.global.dto.RsData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect //클래스가 프록시 역할을 함
@Component
@RequiredArgsConstructor
public class ResponseAspect {

    private final HttpServletResponse response; //응답 객체-> 응답 관련 정보, 기능

    @Around("""
            (
                within
                (
                    @org.springframework.web.bind.annotation.RestController *
                )
                &&
                (
                    @annotation(org.springframework.web.bind.annotation.GetMapping)
                    ||
                    @annotation(org.springframework.web.bind.annotation.PostMapping)
                    ||
                    @annotation(org.springframework.web.bind.annotation.PutMapping)
                    ||
                    @annotation(org.springframework.web.bind.annotation.DeleteMapping)
                )
            )
            ||
            @annotation(org.springframework.web.bind.annotation.ResponseBody)
            """)
    public Object responseAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        Object rst = joinPoint.proceed(); // 실제 수행 메서드

        if(rst instanceof RsData rsData) {
            int statusCode = rsData.getStatusCode();
            response.setStatus(statusCode);
        }

        return rst;
    }

}