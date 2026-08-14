package com.back.p67260811.domain.post.post.controller;


import com.back.p67260811.domain.post.post.dto.PostDto;
import com.back.p67260811.domain.post.post.entity.Post;
import com.back.p67260811.domain.post.post.service.PostService;
import com.back.p67260811.global.dto.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name="ApiV1PostController", description = "글 API")
public class ApiV1PostController {

    private final PostService postService;

    @GetMapping
    @Operation(summary = "글 다건 조회")
    public List<PostDto> list() {
        List<Post> postList = postService.findAll();

        List<PostDto> postDtoList = postList.stream()
                .map(PostDto::new)
                .toList();

        return postDtoList;
    }

    @GetMapping("/{id}")
    @Operation(summary = "글 단건 조회")
    public PostDto detail(
            @PathVariable int id
    ) {
        Post post = postService.findById(id).get();

        return new PostDto(post);
    }

    record PostWriteReqBody(
            @Size(min = 2, max = 10, message = "제목은 2글자 이상 10글자 이하로 작성해 주세요.")
            @NotBlank(message = "제목을 입력해 주세요.")
            String title,
            @Size(min = 2, max = 10, message = "내용은 2글자 이상 10글자 이하로 작성해 주세요.")
            @NotBlank(message = "내용을 입력해 주세요.")
            String content
    ) {
    }

    @PostMapping
    @Operation(summary = "글 작성")
    public RsData<PostDto> write(
            @Valid @RequestBody PostWriteReqBody reqBody
    ) {
        Post post = postService.write(reqBody.title, reqBody.content);
        return new RsData<>(
                "201-1",
                "%d번 글이 성공적으로 등록되었습니다.".formatted(post.getId()),
                new PostDto(post)
        );
    }

    record PostModifyReqBody(
            @Size(min = 2, max = 10, message = "제목은 2글자 이상 10글자 이하로 작성해 주세요.")
            @NotBlank(message = "제목을 입력해 주세요.")
            String title,
            @Size(min = 2, max = 10, message = "내용은 2글자 이상 10글자 이하로 작성해 주세요.")
            @NotBlank(message = "내용을 입력해 주세요.")
            String content
    ) {
    }

    @PatchMapping("/{id}")
    @Transactional
    @Operation(summary = "글 수정")
    public RsData<Void> modify(
            @PathVariable int id,
            @Valid @RequestBody PostModifyReqBody reqBody
    ) {
        Post post = postService.findById(id).get();
        postService.modify(post, reqBody.title, reqBody.content);

        return new RsData<>(
                "200-1",
                "%d번 게시물이 수정되었습니다.".formatted(id)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "글 삭제")
    public RsData<Void> delete(
            @PathVariable int id
    ) {
        Post post = postService.findById(id).get();
        postService.delete(id);

        return new RsData<Void>(
                "200-1",
                "%d번 게시물이 삭제되었습니다.".formatted(id)
        );
    }
}