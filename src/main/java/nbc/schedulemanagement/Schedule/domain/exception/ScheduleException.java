package nbc.schedulemanagement.Schedule.domain.exception;

import nbc.schedulemanagement.common.Exception.BusinessException;
import nbc.schedulemanagement.common.Exception.ErrorCode;

public class ScheduleException extends BusinessException {

    private ScheduleException(ErrorCode errorCode) {
        super(errorCode);
    }

    // ─── 일정 없음 (404) ───────────────────────────────────
    public static class NotFound extends ScheduleException {
        public NotFound() {
            super(ErrorCode.SCHEDULE_NOT_FOUND);
        }
    }

    // ─── 비밀번호 불일치 (403) ─────────────────────────────
    public static class InvalidPassword extends ScheduleException {
        public InvalidPassword() {
            super(ErrorCode.INVALID_PASSWORD);
        }
    }

    // ─── 입력값 오류 (400) ─────────────────────────────────
    public static class InvalidInput extends ScheduleException {
        public InvalidInput() {
            super(ErrorCode.INVALID_INPUT);
        }
    }
}
