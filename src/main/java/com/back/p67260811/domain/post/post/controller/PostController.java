package com.back.p67260811.domain.post.post.controller;


import com.back.p67260811.domain.post.post.dto.PostDto;
import com.back.p67260811.domain.post.post.entity.Post;
import com.back.p67260811.domain.post.post.service.PostService;
import com.back.p67260811.global.dto.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDto> list(){
        List<Post> postList = postService.findAll();

        List<PostDto> postDtoList = postList.stream()
                .map(PostDto::new)
                .toList();

    return postDtoList;
    }

    @GetMapping("/{id}")
    public PostDto detail(
            @PathVariable int id
    ){
        Post post = postService.findById(id).get();
        return new PostDto(post);
    }

    record PostWriteReqBody(
            String title,
            String content
    ){}

    @PostMapping
    public RsData<PostDto> write(
            @Valid @RequestBody PostWriteReqBody reqBody
    ){
        Post post = postService.write(reqBody.title, reqBody.content);
        return new RsData<>(
                "201-1",
                "%d번 글이 성공적으로 등록되었습니다.".formatted(post.getId()),
                new PostDto(post)
                );
    }

    @DeleteMapping("/{id}")
    public RsData<Void> delete(
            @PathVariable int id
    ){
        Post post = postService.findById(id).get();
        postService.delete(id);

        return new RsData<Void>(
                "204-1",
                "게시물이 삭제되었습니다."
        );
    }
}