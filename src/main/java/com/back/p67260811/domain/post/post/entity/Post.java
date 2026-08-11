package com.back.p67260811.domain.post.post.entity;

import com.back.p67260811.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Post extends BaseEntity {
    private String title;
    private String content;
}
