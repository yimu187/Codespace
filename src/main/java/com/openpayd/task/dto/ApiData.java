package com.openpayd.task.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApiData extends Dto{
    private String base;
    private LocalDate date;
    private List<Rate> rates = new ArrayList<>();

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Rate> getRates() {
        return rates;
    }
}
