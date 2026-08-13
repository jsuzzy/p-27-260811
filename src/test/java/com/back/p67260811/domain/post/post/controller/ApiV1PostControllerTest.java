package com.back.p67260811.domain.post.post.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest //빈이 다 등록되면 테스트를 진행해라
@ActiveProfiles("test")
@AutoConfigureMockMvc //스프링부트 웹서버를 모방한 테스트용 서버 역할 객체
@Transactional //db에 적용된 내용이 원복됨
public class ApiV1PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void t1() {

    }

}
