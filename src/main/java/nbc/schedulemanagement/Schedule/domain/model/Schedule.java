package nbc.schedulemanagement.Schedule.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nbc.schedulemanagement.Schedule.domain.exception.ScheduleException;
import nbc.schedulemanagement.common.Exception.ErrorCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

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

    // ── 생성 ──────────────────────────────────────────────
    public static Schedule of(String title, String content,
                              String author, String password) {
        Schedule schedule = new Schedule();

        schedule.title    = title;
        schedule.content  = content;
        schedule.author   = author;
        schedule.password = password;
        return schedule;
    }

    // ── 행위 ──────────────────────────────────────────────
    public void update(String title, String author, String password) {
        validatePassword(password);
        if (title  != null && !title.isBlank())  this.title  = title;
        if (author != null && !author.isBlank()) this.author = author;
    }

    public void validatePassword(String password) {
        if (!this.password.equals(password)) {
            throw ScheduleException.of(ErrorCode.INVALID_PASSWORD);
        }
    }
}