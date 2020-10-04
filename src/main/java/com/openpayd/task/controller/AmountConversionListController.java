package com.openpayd.task.controller;

import com.openpayd.task.dto.ConversionResponseDto;
import com.openpayd.task.service.AmountConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/converted")
public class AmountConversionListController {

    @Autowired
    AmountConversionService amountConversionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelMap doGetAllConvertedData(
            @RequestParam(required = false) String transactionId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate  transactionDate,
            @RequestParam Integer pageLength,
            @RequestParam Integer pageNumber
            ){
        ModelMap result = new ModelMap();

        List<ConversionResponseDto> responseDtos = amountConversionService.findAllAmountCoversionByParams(transactionId, transactionDate, pageLength, pageNumber);

        result.addAttribute("message", "Operation Succeeded");
        result.addAttribute("data", responseDtos);
        result.addAttribute("success", true);

        return result;
    }
}
