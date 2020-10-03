package com.openpayd.task.excption;

public class RateApiException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private final String exceptionMessage;
    private final Throwable ex;

    public RateApiException(String exceptionMessage) {
        super(exceptionMessage);
        ex = null;
        this.exceptionMessage = exceptionMessage;
    }

    public RateApiException(Throwable ex) {
        super(ex);
        this.ex = ex;
        exceptionMessage = ex.getMessage();
    }

    public RateApiException(String exceptionMessage, Throwable ex) {
        super(ex);
        this.ex = ex;
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public Throwable getException() {
        return ex;
    }
}
