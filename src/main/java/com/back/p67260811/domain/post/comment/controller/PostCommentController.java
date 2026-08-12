package com.back.p67260811.domain.post.comment.controller;

import com.back.p67260811.domain.post.comment.entity.PostComment;
import com.back.p67260811.domain.post.post.entity.Post;
import com.back.p67260811.domain.post.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class PostCommentController {

    private final PostService postService;

    record CommentWriteForm (
        @NotBlank(message = "댓글 내용을 입력해주세요.")
        @Size(min = 2, max = 100, message = "댓글 내용은 2글자 이상 100글자 이하로 입력해주세요.")
        String content
    ){}

    @PostMapping("/posts/{postId}/comments/write")
    @Transactional
    @ResponseBody
    public String write(
            @PathVariable int postId,
            @Valid CommentWriteForm form
    ) {

        Post post = postService.findById(postId).get();
//        PostComment postComment = post.addComment(form.content); //메서드가 끝난 후에 db에 반영됨
        PostComment postComment = postService.writeComment(post, form.content);

        //db 저장
        postService.flush();

        return "%d번 댓글이 성공적으로 등록되었습니다.".formatted(postComment.getId()) + postId; //아직 db에 저장되지 않음 -> id는 존재하지 않아서 0
    }

    @GetMapping("/posts/{postId}/comments/{commentId}/delete")
    @Transactional
    @ResponseBody
    public String delete(
            @PathVariable int postId,
            @PathVariable int commentId
    ) {

        Post post = postService.findById(postId).get();
        postService.deleteComment(post, commentId);

        return "%d번 댓글이 삭제되었습니다.".formatted(commentId) + postId;
    }

    record CommentModifyForm(
            @NotBlank(message = "댓글 내용을 입력해주세요.")
            @Size(min = 2, max = 100, message = "댓글 내용은 2글자 이상 100글자 이하로 입력해주세요.")
            String content
    ){}

    @GetMapping("/posts/{postId}/comments/{commentId}/modify")
    @Transactional
    public String modify(
            @PathVariable int postId,
            @PathVariable int commentId,
            @Valid CommentModifyForm form
    ) {

        Post post = postService.findById(postId).get();
        postService.modifyComment(post, commentId, form.content);

        return "%d번 댓글이 수정되었습니다.".formatted(commentId) + postId;
    }
}
