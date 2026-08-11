package com.back.p67260811.global.initData;

import com.back.p67260811.domain.post.post.entity.Post;
import com.back.p67260811.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    private final PostService postService;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    ApplicationRunner initDataRunner() {
        return args -> {
            self.work1();
        };

    }

    @Transactional //post 내용이 바뀔 때마다 자동으로 더티체킹 발생
    public void work1(){
        if(postService.count() > 0) {
            return;
        }

        //자바스럽게 짜도 db 반영됨
        Post post1 = postService.write("제목1", "내용1");
        Post post2 = postService.write("제목2", "내용2");
        Post post3 = postService.write("제목3", "내용3");

        post1.addComment("댓글 1-1");
        post1.addComment("댓글 1-2");
        post1.addComment("댓글 1-3");
        post2.addComment("댓글 2-1");
        post2.addComment("댓글 2-2");
    }
}
