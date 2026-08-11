package com.back.p67260811.domain.post.post.controller;


import com.back.p67260811.domain.post.post.entity.Post;
import com.back.p67260811.domain.post.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    @ResponseBody
    public List<Post> list(){
        List<Post> postList = postService.findAll();
        return postList;
    }

    @GetMapping("/posts/write")
    @ResponseBody
    public String write() {

        return getWriteFormHtml("", "", "");
    }

    @AllArgsConstructor
    @Getter
    public static class  PostWriteForm{
        @NotBlank(message = "1-제목을 입력해주세요.")
        @Size(min=2, max=10, message = "2-제목은 2자 이상 10자 이하로 입력해주세요.")
        private String title;
        @NotBlank(message = "3-내용을 입력해주세요.")
        @Size(min=2, max=10, message = "4-내용은 2자 이상 10자 이하로 입력해주세요.")
        private String content;
    }

    @PostMapping("/posts/doWrite")
    @ResponseBody
    public String doWrite(
            @Valid PostWriteForm postWriteForm,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {

            String errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(f -> f.getDefaultMessage() + "-" + f.getField())
                    .sorted()
                    .map(message -> message.split("-")) // [no, errorMessage, errorFiled]
                    .map(bits -> """
                            <!-- %s --><li data-error-field-name="%s">%s</li>
                            """.formatted(bits[0], bits[2], bits[1]))
                    .collect(Collectors.joining(""));

            return getWriteFormHtml(errorMessages,
                    postWriteForm.getTitle(),
                    postWriteForm.getContent()
            );
        }

        Post post = postService.write(postWriteForm.getTitle(), postWriteForm.getContent());
        return "%d번 글이 작성되었습니다.".formatted(post.getId());
    }

    private String getWriteFormHtml(String errorMessage, String title, String content) {
        return """
                        <ul style="color:red">%s</ul>
                
                        <form method="POST" action="/posts/doWrite">
                          <input type="text" name="title" value="%s" autoFocus>
                          <br>
                          <textarea name="content">%s</textarea>
                          <input type="submit" value="작성">
                        </form>
                
                        <script>
                            const li = document.querySelector("ul li");
                            const errorFieldName = li.dataset.errorFieldName;
                
                            if(errorFieldName.length > 0) {
                                const form = document.querySelector("form");
                                form[errorFieldName].focus();
                            }
                        </script>
                """.formatted(errorMessage, title, content);
    }

}