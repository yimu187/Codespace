package com.openpayd.task.excption;

public class ConversionNoRateException extends RateApiException {
    public ConversionNoRateException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
