package nbc.schedulemanagement.Schedule.domain.exception;

import nbc.schedulemanagement.common.Exception.BusinessException;
import nbc.schedulemanagement.common.Exception.ErrorCode;

public class ScheduleException extends BusinessException {

    private ScheduleException(ErrorCode errorCode) {
        super(errorCode);
    }

    private ScheduleException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }

    public static ScheduleException of(ErrorCode errorCode) {
        return new ScheduleException(errorCode);
    }

    public static ScheduleException of(ErrorCode errorCode, String detail) {
        return new ScheduleException(errorCode, detail);
    }
}
