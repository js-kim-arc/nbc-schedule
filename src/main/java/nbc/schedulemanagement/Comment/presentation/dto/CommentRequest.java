package nbc.schedulemanagement.Comment.presentation.dto;

public class CommentRequest {

    // ─── 1. 댓글 생성 ─────────────────────────────────────
    public record Create(
            String content,
            String author,
            String password
    ) {}

    // ─── 2. 댓글 수정 ─────────────────────────────────────
    public record Update(
            String content,
            String author,
            String password
    ) {}

    // ─── 3. 댓글 삭제 ─────────────────────────────────────
    public record Delete(
            String password
    ) {}
}
