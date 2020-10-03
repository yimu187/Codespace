package com.openpayd.task.controller;

import com.openpayd.task.dto.ApiData;
import com.openpayd.task.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/rate")
public class RateController {

    @Autowired
    RateService rateService;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ModelMap doGetRateData(
            @RequestParam String sourceCurrency,
            @RequestParam String targetCurrency
    ){
        ModelMap result = new ModelMap();

        BigDecimal apiDataResult = rateService.getApiDataResult(sourceCurrency, targetCurrency);

        result.addAttribute("message", "Operation Succedded");
        result.addAttribute("result", apiDataResult);
        result.addAttribute("success", true);

        return result;
    }
}
