package com.back.p67260811.domain.post.post.controller;

import com.back.p67260811.domain.post.post.entity.Post;
import com.back.p67260811.domain.post.post.repository.PostRepository;
import com.back.p67260811.domain.post.post.service.PostService;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest //빈이 다 등록되면 테스트를 진행해라
@ActiveProfiles("test")
@AutoConfigureMockMvc //스프링부트 웹서버를 모방한 테스트용 서버 역할 객체
@Transactional //db에 적용된 내용이 원복됨
public class ApiV1PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostService postService;

    @Test
    @DisplayName("글 다건 조회")
    void t1() throws Exception {

        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/posts")
                )
                .andDo(print()); //응답을 콘솔에 출력

        List<Post> posts = postRepository.findAll();

        resultActions
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("list"))
                .andExpect(status().isOk());

        for(int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);

            resultActions
                    .andExpect(jsonPath("$[%d].id".formatted(i)).value(post.getId()))
                    .andExpect(jsonPath("$[%d].createDate".formatted(i)).exists())
                    .andExpect(jsonPath("$[%d].modifyDate".formatted(i)).exists())
                    .andExpect(jsonPath("$[%d].title".formatted(i)).value(post.getTitle()))
                    .andExpect(jsonPath("$[%d].content".formatted(i)).value(post.getContent()));
        }
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

    @Test
    @DisplayName("글 수정")
    void t3() throws Exception {
        int targetId = 1;
        String title = "제목 수정";
        String content = "내용 수정";

        ResultActions resultActions = mvc
                .perform(
                        patch("/api/v1/posts/%d".formatted(targetId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title": "%s",
                                            "content": "%s"
                                        }
                                        """.formatted(title, content))
                )
                .andDo(print());

        // 필수 검증
        resultActions
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("modify"))
                .andExpect(status().isOk());

        // 선택적 검증
        Post post = postRepository.findById(targetId).get(); //순수 DB 조회
        Post psot2 = postService.findById(targetId).get(); //비즈니스 로직 포함

        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("글 단건조회")
    void t4() throws Exception {
        int targetId = 2;

        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/posts/%d".formatted(targetId))
                )
                .andDo(print());

        // 필수 검증
        resultActions
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("detail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.createDate").exists())
                .andExpect(jsonPath("$.modifyDate").exists())
                .andExpect(jsonPath("$.title").value("제목2"))
                .andExpect(jsonPath("$.content").value("내용2"));

    }


}
