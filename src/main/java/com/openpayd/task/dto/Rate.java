package com.openpayd.task.dto;

import java.math.BigDecimal;

public class Rate extends Dto{

    private String currency;
    private BigDecimal value;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
