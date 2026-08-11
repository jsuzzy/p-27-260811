package com.back.p67260811.domain.post.post.service;

import com.back.p67260811.domain.post.post.entity.Post;
import com.back.p67260811.domain.post.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post write(String title, String content) {
        Post post = new Post(title, content);
        postRepository.save(post);
        return post;
    }

    public Optional<Post> findById(int postId) {
        return postRepository.findById(postId);
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public long count() {
        return postRepository.count();
    }
    public void flush(){
        postRepository.flush();
    }
}
