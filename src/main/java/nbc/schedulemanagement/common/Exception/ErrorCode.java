package nbc.schedulemanagement.common.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // ─── Schedule ──────────────────────────────────────────
    SCHEDULE_NOT_FOUND(404, "해당 일정을 찾을 수 없습니다."),
    INVALID_PASSWORD(403,   "비밀번호가 일치하지 않습니다."),
    INVALID_INPUT(400,      "입력값이 올바르지 않습니다.");

    private final int status;
    private final String message;
}