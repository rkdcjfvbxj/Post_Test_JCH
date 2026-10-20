package com.example.board.service;
import com.example.board.dto.Post;
import com.example.board.dto.Page;
import java.util.List;

public interface PostService {
    int insertPost(Post post);
    List<Post> selectPostList(Page page);
    long countPosts();
    Post selectPostById(Long id);
    int updatePost(Post post);
    int deletePost(Long id);
}
