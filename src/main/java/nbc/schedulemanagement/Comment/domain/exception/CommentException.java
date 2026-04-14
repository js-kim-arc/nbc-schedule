package nbc.schedulemanagement.Comment.domain.exception;

import nbc.schedulemanagement.common.Exception.BusinessException;
import nbc.schedulemanagement.common.Exception.ErrorCode;

public class CommentException extends BusinessException {

    private CommentException(ErrorCode errorCode) {
        super(errorCode);
    }

    private CommentException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }

    public static CommentException of(ErrorCode errorCode) {
        return new CommentException(errorCode);
    }

    public static CommentException of(ErrorCode errorCode, String detail) {
        return new CommentException(errorCode, detail);
    }
}

