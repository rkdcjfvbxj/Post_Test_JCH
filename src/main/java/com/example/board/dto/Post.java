package com.example.board.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Post {
    private Long id;
    @NotBlank(message = "필수 입력 항목입니다.")
    @Size(max = 200, message = "입력 가능한 길이를 초과했습니다.")
    private String title;
    @NotBlank(message = "필수 입력 항목입니다.")
    @Size(max = 10000, message = "입력 가능한 길이를 초과했습니다.")
    private String content;
    @NotBlank(message = "필수 입력 항목입니다.")
    @Size(max = 50, message = "입력 가능한 길이를 초과했습니다.")
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
