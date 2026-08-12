package com.back.p67260811.domain.post.post.controller;


import com.back.p67260811.domain.post.post.dto.PostDto;
import com.back.p67260811.domain.post.post.entity.Post;
import com.back.p67260811.domain.post.post.service.PostService;
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

}