package com.example.board.dto;

public class Page {
    private final int page;
    private final int size;
    private final long totalCount;
    private final int totalPages;
    private final int startPage;
    private final int endPage;

    public Page(int requestedPage, int size, long totalCount) {
        if (size < 1 || totalCount < 0) {
            throw new IllegalArgumentException("잘못된 페이징 값입니다.");
        }
        this.size = size;
        this.totalCount = totalCount;
        this.totalPages = Math.toIntExact(Math.max(1L,
                totalCount / size + (totalCount % size == 0 ? 0 : 1)));
        this.page = Math.max(1, Math.min(requestedPage, totalPages));
        this.startPage = ((page - 1) / 5) * 5 + 1;
        this.endPage = Math.min(startPage + 4, totalPages);
    }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalCount() { return totalCount; }
    public int getTotalPages() { return totalPages; }
    public int getStartPage() { return startPage; }
    public int getEndPage() { return endPage; }
    public long getOffset() { return (long) (page - 1) * size; }
    public boolean isHasPrevious() { return startPage > 1; }
    public boolean isHasNext() { return endPage < totalPages; }
}
