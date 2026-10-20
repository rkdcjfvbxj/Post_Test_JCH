package com.example.board.service;
import com.example.board.dto.Post;
import com.example.board.dto.Page;
import java.util.List;
import com.example.board.mapper.PostMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;
    public PostServiceImpl(PostMapper postMapper) { this.postMapper = postMapper; }

    @Override
    @Transactional
    public int insertPost(Post post) {
        return postMapper.insertPost(post);
    }

    @Override
    public List<Post> selectPostList(Page page) {
        return postMapper.selectPostList(page);
    }

    @Override
    public long countPosts() {
        return postMapper.countPosts();
    }

    @Override
    public Post selectPostById(Long id) {
        return postMapper.selectPostById(id);
    }

    @Override
    @Transactional
    public int updatePost(Post post) {
        return postMapper.updatePost(post);
    }

    @Override
    @Transactional
    public int deletePost(Long id) {
        return postMapper.deletePost(id);
    }
}
