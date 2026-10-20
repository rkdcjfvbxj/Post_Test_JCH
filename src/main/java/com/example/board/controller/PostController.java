package com.example.board.controller;

import com.example.board.dto.Page;
import com.example.board.dto.Post;
import com.example.board.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/Post")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) { this.postService = postService; }

    @GetMapping("/list")
    public String list(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        Page pageInfo = new Page(page, 10, postService.countPosts());
        model.addAttribute("posts", postService.selectPostList(pageInfo));
        model.addAttribute("pageInfo", pageInfo);
        return "Post/list";
    }

    @GetMapping("/read")
    public String read(@RequestParam("PostNo") Long id, Model model) {
        model.addAttribute("post", findPost(id));
        return "Post/read";
    }

    @GetMapping("/insert")
    public String insertForm(Model model) {
        model.addAttribute("post", new Post());
        return "Post/insert";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("post") Post post, BindingResult errors) {
        if (errors.hasErrors()) { return "Post/insert"; }
        post.setId(null);
        postService.insertPost(post);
        return "redirect:/Post/list";
    }

    @GetMapping("/update")
    public String updateForm(@RequestParam("PostNo") Long id, Model model) {
        model.addAttribute("post", findPost(id));
        return "Post/update";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("post") Post post, BindingResult errors) {
        Post existing = findPost(post.getId());
        // 작성자는 기존 값을 유지한다.
        if (errors.hasFieldErrors("title") || errors.hasFieldErrors("content")) {
            post.setWriter(existing.getWriter());
            return "Post/update";
        }
        post.setWriter(existing.getWriter());
        if (postService.updatePost(post) == 0) { throw notFound(); }
        return "redirect:/Post/read?PostNo=" + post.getId();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("PostNo") Long id) {
        if (postService.deletePost(id) == 0) { throw notFound(); }
        return "redirect:/Post/list";
    }

    private Post findPost(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "글번호가 필요합니다.");
        }
        Post post = postService.selectPostById(id);
        if (post == null) { throw notFound(); }
        return post;
    }
    private ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
    }
}
