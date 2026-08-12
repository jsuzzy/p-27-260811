package com.back.p67260811.domain.post.post.dto;

import com.back.p67260811.domain.post.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private int id;
    private String title;
    private String body;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    public PostDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.body = post.getContent();
        this.createDate = post.getCreateDate();
        this.modifyDate = post.getModifyDate();
    }
}
