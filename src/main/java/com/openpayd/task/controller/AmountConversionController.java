package com.openpayd.task.controller;

import com.openpayd.task.dto.ConversionResponseDto;
import com.openpayd.task.service.AmountConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/conversion")
public class AmountConversionController {

    @Autowired
    AmountConversionService conversionService;

    @RequestMapping(value = "/amount", method = RequestMethod.GET)
    public ModelMap doGetConvertedData(
            @RequestParam BigDecimal sourceAmount,
            @RequestParam String sourceCurrency,
            @RequestParam String targetCurrency
    ){
        ModelMap result = new ModelMap();

        ConversionResponseDto responseDto = conversionService.getConversionResponseDto(sourceAmount, sourceCurrency, targetCurrency);

        result.addAttribute("message", "Operation Succeeded");
        result.addAttribute("data", responseDto);
        result.addAttribute("success", true);

        return result;
    }

}
