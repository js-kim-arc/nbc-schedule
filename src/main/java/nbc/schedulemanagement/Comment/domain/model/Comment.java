package nbc.schedulemanagement.Comment.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nbc.schedulemanagement.Comment.domain.exception.CommentException;
import nbc.schedulemanagement.common.Exception.ErrorCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연관관계 매핑 없이 FK를 Long 값으로 보유
    @Column(nullable = false, updatable = false)
    private Long scheduleId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static Comment of(Long scheduleId, String content,
                             String author, String password) {
        Comment comment = new Comment();
        comment.scheduleId = scheduleId;
        comment.content    = content;
        comment.author     = author;
        comment.password   = password;
        return comment;
    }

    // ── 행위 ──────────────────────────────────────────────
    public void update(String content, String author, String password) {
        validatePassword(password);
        if (content != null && !content.isBlank()) this.content = content;
        if (author  != null && !author.isBlank())  this.author  = author;
    }

    public void validatePassword(String password) {
        if (!this.password.equals(password)) {
            throw CommentException.of(ErrorCode.INVALID_PASSWORD);
        }
    }
}