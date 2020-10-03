package com.openpayd.task.excption;

public class RatiApiClientErrorException extends RateApiException {

    public RatiApiClientErrorException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
