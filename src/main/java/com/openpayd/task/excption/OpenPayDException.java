package com.openpayd.task.excption;

public class OpenPayDException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private final String exceptionMessage;
    private final Throwable ex;

    public OpenPayDException(String exceptionMessage) {
        super(exceptionMessage);
        ex = null;
        this.exceptionMessage = exceptionMessage;
    }

    public OpenPayDException(Throwable ex) {
        super(ex);
        this.ex = ex;
        exceptionMessage = ex.getMessage();
    }

    public OpenPayDException(String exceptionMessage, Throwable ex) {
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
