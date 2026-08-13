package com.back.p67260811.domain.post.post.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest //빈이 다 등록되면 테스트를 진행해라
@ActiveProfiles("test")
@AutoConfigureMockMvc //스프링부트 웹서버를 모방한 테스트용 서버 역할 객체
@Transactional //db에 적용된 내용이 원복됨
public class ApiV1PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("글 다건 조회")
    void t1() throws Exception {

        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/posts")
                )
                .andDo(print()); //응답을 콘솔에 출력

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("글 생성")
    void t2() throws Exception {
        String title = "제목입니다";
        String content = "내용입니다";

        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title": "%s",
                                            "content": "%s"
                                        }
                                        """.formatted(title, content))
                )
                .andDo(print());

        resultActions
                .andExpect(status().isCreated());
    }

}
