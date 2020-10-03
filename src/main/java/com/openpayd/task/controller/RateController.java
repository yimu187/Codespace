package com.openpayd.task.controller;

import com.openpayd.task.dto.ApiData;
import com.openpayd.task.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rate")
public class RateController {

    @Autowired
    RateService rateService;

    @RequestMapping(value = "/apiData", method = RequestMethod.GET)
    public ModelMap doGetApiData(
            @RequestParam(required = false) String base,
            @RequestParam(required = false) List<String> symbols
    ){
        ModelMap result = new ModelMap();

        ApiData apiDataResult = rateService.getApiDataResult(base, symbols);

        result.addAttribute("message", "Operation Succedded");
        result.addAttribute("data", apiDataResult);
        result.addAttribute("success", true);

        return result;
    }
}
