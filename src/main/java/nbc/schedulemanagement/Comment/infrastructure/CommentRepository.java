package nbc.schedulemanagement.Comment.infrastructure;

import nbc.schedulemanagement.Comment.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 일정의 댓글 전체 조회 (작성일 오름차순)
    List<Comment> findByScheduleIdOrderByCreatedAtAsc(Long scheduleId);

    // 특정 일정의 댓글 수 (10개 제한 검증용)
    int countByScheduleId(Long scheduleId);
}
