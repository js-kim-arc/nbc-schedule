package nbc.schedulemanagement.Comment.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import nbc.schedulemanagement.Comment.domain.model.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        Long scheduleId,
        String content,
        String author,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime updatedAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getScheduleId(),
                comment.getContent(),
                comment.getAuthor(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
