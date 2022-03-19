package org.setana.treenity.exception;

public class NotAcceptableException extends BusinessException {

    public NotAcceptableException(ErrorCode errorCode) {
        super(errorCode);
    }
}
