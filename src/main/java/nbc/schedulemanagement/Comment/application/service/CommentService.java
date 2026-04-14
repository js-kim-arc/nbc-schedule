package nbc.schedulemanagement.Comment.application.service;


import lombok.RequiredArgsConstructor;
import nbc.schedulemanagement.Comment.domain.exception.CommentException;
import nbc.schedulemanagement.Comment.domain.model.Comment;
import nbc.schedulemanagement.Comment.infrastructure.CommentRepository;
import nbc.schedulemanagement.Comment.presentation.dto.CommentRequest;
import nbc.schedulemanagement.Comment.presentation.dto.CommentResponse;
import nbc.schedulemanagement.Schedule.domain.exception.ScheduleException;
import nbc.schedulemanagement.Schedule.infrastructure.ScheduleRepository;
import nbc.schedulemanagement.common.Exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private static final int MAX_COMMENT_COUNT = 10;

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;  // 연관관계 없이 직접 조회

    // ── 생성 ──────────────────────────────────────────────
    @Transactional
    public CommentResponse create(Long scheduleId, CommentRequest.Create request) {
        validateCreate(request);
        verifyScheduleExists(scheduleId);
        verifyCommentLimit(scheduleId);

        Comment comment = Comment.of(
                scheduleId,
                request.content(),
                request.author(),
                request.password()
                                    );
        return CommentResponse.from(commentRepository.save(comment));
    }

    // ── 일정별 댓글 전체 조회 ────────────────────────────
    public List<CommentResponse> findAllByScheduleId(Long scheduleId) {
        verifyScheduleExists(scheduleId);

        return commentRepository.findByScheduleIdOrderByCreatedAtAsc(scheduleId)
                                .stream()
                                .map(CommentResponse::from)
                                .toList();
    }

    // ── 수정 ──────────────────────────────────────────────
    @Transactional
    public CommentResponse update(Long scheduleId, Long commentId,
                                  CommentRequest.Update request) {
        validateUpdate(request);
        verifyScheduleExists(scheduleId);

        Comment comment = getCommentOrThrow(commentId);
        verifyCommentBelongsToSchedule(comment, scheduleId);
        comment.update(request.content(), request.author(), request.password());
        return CommentResponse.from(comment);
    }

    // ── 삭제 ──────────────────────────────────────────────
    @Transactional
    public void delete(Long scheduleId, Long commentId,
                       CommentRequest.Delete request) {
        validateDelete(request);
        verifyScheduleExists(scheduleId);

        Comment comment = getCommentOrThrow(commentId);
        verifyCommentBelongsToSchedule(comment, scheduleId);
        comment.validatePassword(request.password());
        commentRepository.delete(comment);
    }

    // ── 수동 검증 ─────────────────────────────────────────
    private void validateCreate(CommentRequest.Create request) {
        if (isBlank(request.content()))  throw CommentException.of(ErrorCode.INVALID_INPUT, "내용은 필수입니다.");
        if (isBlank(request.author()))   throw CommentException.of(ErrorCode.INVALID_INPUT, "작성자는 필수입니다.");
        if (isBlank(request.password())) throw CommentException.of(ErrorCode.INVALID_INPUT, "비밀번호는 필수입니다.");
    }

    private void validateUpdate(CommentRequest.Update request) {
        if (isBlank(request.password())) throw CommentException.of(ErrorCode.INVALID_INPUT, "비밀번호는 필수입니다.");
    }

    private void validateDelete(CommentRequest.Delete request) {
        if (isBlank(request.password())) throw CommentException.of(ErrorCode.INVALID_INPUT, "비밀번호는 필수입니다.");
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    // ── 비즈니스 규칙 검증 ────────────────────────────────
    private void verifyScheduleExists(Long scheduleId) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw ScheduleException.of(ErrorCode.SCHEDULE_NOT_FOUND);
        }
    }

    private void verifyCommentLimit(Long scheduleId) {
        if (commentRepository.countByScheduleId(scheduleId) >= MAX_COMMENT_COUNT) {
            throw CommentException.of(ErrorCode.COMMENT_LIMIT_EXCEEDED);
        }
    }

    // 댓글이 해당 일정 소속인지 확인 (타 일정 댓글 조작 방지)
    private void verifyCommentBelongsToSchedule(Comment comment, Long scheduleId) {
        if (!comment.getScheduleId().equals(scheduleId)) {
            throw CommentException.of(ErrorCode.COMMENT_NOT_FOUND);
        }
    }

    // ── 공통 조회 헬퍼 ────────────────────────────────────
    private Comment getCommentOrThrow(Long id) {
        return commentRepository.findById(id)
                                .orElseThrow(() -> CommentException.of(ErrorCode.COMMENT_NOT_FOUND));
    }
}
