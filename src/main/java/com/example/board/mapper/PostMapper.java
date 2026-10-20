package com.example.board.mapper;
import com.example.board.dto.Post;
import com.example.board.dto.Page;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostMapper {
    int insertPost(Post post);
    List<Post> selectPostList(Page page);
    long countPosts();
    Post selectPostById(@Param("id") Long id);
    int updatePost(Post post);
    int deletePost(@Param("id") Long id);
}
