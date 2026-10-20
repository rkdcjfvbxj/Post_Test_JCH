package com.example.board;

import com.example.board.dto.Page;
import com.example.board.dto.Post;
import com.example.board.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BoardIntegrationTest {
    @Autowired PostService service;
    @Autowired MockMvc mvc;

    private Post create(String title) {
        Post post = new Post();
        post.setTitle(title);
        post.setWriter("작성자");
        post.setContent("첫째 줄\n둘째 줄");
        assertEquals(1, service.insertPost(post));
        assertNotNull(post.getId());
        return post;
    }

    @Test
    void crudAndViewRendering() throws Exception {
        Post post = create("한글 제목 <script>");
        assertEquals(post.getTitle(), service.selectPostById(post.getId()).getTitle());
        mvc.perform(get("/")).andExpect(status().isOk());
        mvc.perform(get("/Post/list")).andExpect(status().isOk())
                .andExpect(view().name("Post/list"));
        mvc.perform(get("/Post/read").param("PostNo", post.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("&lt;script&gt;")));
        mvc.perform(get("/Post/insert")).andExpect(status().isOk());
        mvc.perform(get("/Post/update").param("PostNo", post.getId().toString()))
                .andExpect(status().isOk());
        mvc.perform(post("/Post/update").param("id", post.getId().toString())
                .param("title", "수정 제목").param("content", "수정 내용"))
                .andExpect(status().is3xxRedirection());
        assertEquals("수정 제목", service.selectPostById(post.getId()).getTitle());
        assertEquals("작성자", service.selectPostById(post.getId()).getWriter());
        mvc.perform(post("/Post/delete").param("PostNo", post.getId().toString()))
                .andExpect(redirectedUrl("/Post/list"));
        assertNull(service.selectPostById(post.getId()));
        mvc.perform(get("/Post/read").param("PostNo", post.getId().toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void paginationAndValidation() throws Exception {
        for (int i = 1; i <= 61; i++) { create("제목 " + i); }
        Page first = new Page(1, 10, service.countPosts());
        Page last = new Page(999, 10, service.countPosts());
        assertEquals(10, service.selectPostList(first).size());
        assertEquals("제목 61", service.selectPostList(first).get(0).getTitle());
        assertEquals(7, last.getPage());
        assertEquals(1, service.selectPostList(last).size());
        assertTrue(first.isHasNext());
        assertTrue(last.isHasPrevious());
        mvc.perform(get("/Post/list").param("page", "6"))
                .andExpect(status().isOk());
        mvc.perform(post("/Post/insert").param("title", " ")
                .param("writer", "작성자").param("content", "내용"))
                .andExpect(view().name("Post/insert"))
                .andExpect(model().attributeHasFieldErrors("post", "title"));
        assertEquals(61, service.countPosts());
        mvc.perform(post("/Post/insert").param("title", "등록")
                .param("writer", "작성자").param("content", "내용"))
                .andExpect(redirectedUrl("/Post/list"));
        assertEquals(62, service.countPosts());
        assertEquals(1, new Page(-2, 10, 0).getPage());
        assertEquals(0, new Page(1, 10, 0).getOffset());
    }
}
