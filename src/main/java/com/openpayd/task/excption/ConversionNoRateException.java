package com.openpayd.task.excption;

public class ConversionNoRateException extends OpenPayDException {
    public ConversionNoRateException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
