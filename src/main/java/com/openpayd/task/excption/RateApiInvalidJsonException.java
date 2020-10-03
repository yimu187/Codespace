package com.openpayd.task.excption;

public class RateApiInvalidJsonException extends RateApiException {
    public RateApiInvalidJsonException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
