package com.openpayd.task.service;


import com.openpayd.task.dao.AmountConversionDao;
import com.openpayd.task.dto.ApiData;
import com.openpayd.task.dto.ConversionResponseDto;
import com.openpayd.task.dto.Rate;
import com.openpayd.task.excption.ConversionNoRateException;
import com.openpayd.task.model.AmountConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AmountConversionService {

    @Autowired
    RateService rateService;

    @Autowired
    AmountConversionDao amountConversionDao;

    public ConversionResponseDto getConversionResponseDto(BigDecimal sourceAmount, String sourceCurrency, String targetCurrency) {
        BigDecimal rateValue = rateService.getApiDataResult(sourceCurrency, targetCurrency);
        BigDecimal targetAmount = sourceAmount.multiply(rateValue).setScale(6, RoundingMode.HALF_UP);
        LocalDateTime transactionDate = LocalDateTime.now();

        ConversionResponseDto conversionResponseDto = new ConversionResponseDto();
        conversionResponseDto.setTransactionDate(transactionDate);
        conversionResponseDto.setSourceCurrency(sourceCurrency);
        conversionResponseDto.setSourceAmount(sourceAmount);
        conversionResponseDto.setTargetCurrency(targetCurrency);
        conversionResponseDto.setTargetAmount(targetAmount);

        saveAmountConversion(conversionResponseDto);

        return conversionResponseDto;
    }

    public List<ConversionResponseDto> findAllAmountCoversionByParams(String transactionId, LocalDate transactionDate
            , int pageLength, int pageNumber){
        List<AmountConversion> resultList = amountConversionDao.fetchAllAmountConversionByParams(transactionId, transactionDate, pageLength, pageNumber);

        List<ConversionResponseDto> responseDtos = new ArrayList<>();
        resultList.stream().forEach(amountConversion -> {
            ConversionResponseDto responseDto = new ConversionResponseDto();
            responseDto.setTransactionId(amountConversion.getTransactionId());
            responseDto.setTransactionDate(amountConversion.getTransactionDate());
            responseDto.setSourceCurrency(amountConversion.getSourceCurrency());
            responseDto.setSourceAmount(amountConversion.getSourceAmount());
            responseDto.setTargetCurrency(amountConversion.getTargetCurrency());
            responseDto.setTargetAmount(amountConversion.getTargetAmount());
            responseDtos.add(responseDto);
        });

        return responseDtos;
    }

    public AmountConversion saveAmountConversion(ConversionResponseDto conversionResponseDto){
        AmountConversion amountConversion = new AmountConversion();
        amountConversion.setTransactionDate(conversionResponseDto.getTransactionDate());
        amountConversion.setSourceAmount(conversionResponseDto.getSourceAmount());
        amountConversion.setSourceCurrency(conversionResponseDto.getSourceCurrency());
        amountConversion.setTargetAmount(conversionResponseDto.getTargetAmount());
        amountConversion.setTargetCurrency(conversionResponseDto.getTargetCurrency());

        AmountConversion savedAmountConversion = amountConversionDao.save(amountConversion);

        return savedAmountConversion;
    }
}
