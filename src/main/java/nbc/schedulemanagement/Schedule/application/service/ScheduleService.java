package nbc.schedulemanagement.Schedule.application.service;

import lombok.RequiredArgsConstructor;
import nbc.schedulemanagement.Schedule.domain.exception.ScheduleException;
import nbc.schedulemanagement.Schedule.domain.model.Schedule;
import nbc.schedulemanagement.Schedule.infrastructure.ScheduleRepository;
import nbc.schedulemanagement.Schedule.presentation.dto.ScheduleRequest;
import nbc.schedulemanagement.Schedule.presentation.dto.ScheduleResponse;
import nbc.schedulemanagement.common.Exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // ── 생성 ─────────────────────────────────────────
    @Transactional
    public ScheduleResponse create(ScheduleRequest.Create request) {
        validateCreate(request);

        Schedule schedule = Schedule.of(
                request.title(),
                request.content(),
                request.author(),
                request.password()
                                       );
        return ScheduleResponse.from(scheduleRepository.save(schedule));
    }

    // ── 전체 조회 ─────────────────────────────────────────
    public List<ScheduleResponse> findAll(String author) {
        List<Schedule> schedules = (author != null && !author.isBlank())
                ? scheduleRepository.findByAuthorOrderByUpdatedAtDesc(author)
                : scheduleRepository.findAllByOrderByUpdatedAtDesc();

        return schedules.stream()
                        .map(ScheduleResponse::from)
                        .toList();
    }

    // ── 단건 조회 ─────────────────────────────────────────
    public ScheduleResponse findById(Long id) {
        return ScheduleResponse.from(getScheduleOrThrow(id));
    }

    // ── 수정 ──────────────────────────────────────────────
    @Transactional
    public ScheduleResponse update(Long id, ScheduleRequest.Update request) {
        validateUpdate(request);

        Schedule schedule = getScheduleOrThrow(id);
        schedule.update(request.title(), request.author(), request.password());
        return ScheduleResponse.from(schedule);
    }

    // ── 삭제 ──────────────────────────────────────────────
    @Transactional
    public void delete(Long id, ScheduleRequest.Delete request) {
        validateDelete(request);

        Schedule schedule = getScheduleOrThrow(id);
        schedule.validatePassword(request.password());
        scheduleRepository.delete(schedule);
    }

    // ── 수동 검증 ─────────────────────────────────────────
    private void validateCreate(ScheduleRequest.Create request) {
        if (isBlank(request.title()))    throw ScheduleException.of(ErrorCode.INVALID_INPUT, "제목은 필수입니다.");
        if (isBlank(request.content()))  throw ScheduleException.of(ErrorCode.INVALID_INPUT, "내용은 필수입니다.");
        if (isBlank(request.author()))   throw ScheduleException.of(ErrorCode.INVALID_INPUT, "작성자는 필수입니다.");
        if (isBlank(request.password())) throw ScheduleException.of(ErrorCode.INVALID_INPUT, "비밀번호는 필수입니다.");
    }

    private void validateUpdate(ScheduleRequest.Update request) {
        if (isBlank(request.password())) throw ScheduleException.of(ErrorCode.INVALID_INPUT, "비밀번호는 필수입니다.");
    }

    private void validateDelete(ScheduleRequest.Delete request) {
        if (isBlank(request.password())) throw ScheduleException.of(ErrorCode.INVALID_INPUT, "비밀번호는 필수입니다.");
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }


    // ── 공통 조회 헬퍼 ────────────────────────────────────
    private Schedule getScheduleOrThrow(Long id) {
        return scheduleRepository.findById(id)
                                 .orElseThrow(() -> ScheduleException.of(ErrorCode.SCHEDULE_NOT_FOUND));
    }
}
