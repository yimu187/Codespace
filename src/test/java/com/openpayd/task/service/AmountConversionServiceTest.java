package com.openpayd.task.service;

import com.openpayd.task.dto.ConversionResponseDto;
import com.openpayd.task.model.AmountConversion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class AmountConversionServiceTest {

    @Autowired
    AmountConversionService amountConversionService;

    @Test
    public void getConversionResponseDtoTest(){
        BigDecimal sourceAmount = BigDecimal.TEN;
        String sourceCurrency = "USD";
        String targetCurrency = "TRY";

        ConversionResponseDto conversionResponseDto
                = amountConversionService.getConversionResponseDto(sourceAmount, sourceCurrency, targetCurrency);

        Assert.notNull(conversionResponseDto, "Object Empty");
    }

    @Test
    public void findAllAmountCoversionTest(){
        saveAmountConversion();
        saveAmountConversion();
        List<ConversionResponseDto> allAmountCoversion = amountConversionService.findAllAmountCoversionByParams(null, null, 5, 0);
        Assert.notEmpty(allAmountCoversion, "Empty Object");
    }

    @Test
    public void findAllAmountCoversionByDateTest(){
        LocalDate now = LocalDate.now();
        saveAmountConversion();
        saveAmountConversion();
        List<ConversionResponseDto> allAmountCoversion = amountConversionService.findAllAmountCoversionByParams(null, now,5 ,0);
        Assert.notEmpty(allAmountCoversion, "Empty List");
    }

    @Test
    public void findAllAmountCoversionByIdTest(){
        saveAmountConversion();
        AmountConversion amountConversion = saveAmountConversion();
        List<ConversionResponseDto> allAmountCoversion = amountConversionService.findAllAmountCoversionByParams(amountConversion.getTransactionId(), null,5 ,0);
        Assert.notEmpty(allAmountCoversion, "Empty List");
    }

    @Test
    public void findAllAmountCoversionByIdAndDateTest(){
        LocalDate now = LocalDate.now();
        saveAmountConversion();
        AmountConversion amountConversion = saveAmountConversion();
        List<ConversionResponseDto> allAmountCoversion = amountConversionService.findAllAmountCoversionByParams(amountConversion.getTransactionId(), now,5 ,0);
        Assert.notEmpty(allAmountCoversion, "Empty Lİst");
    }

    @Test
    public void saveAmountConversionTest(){
        AmountConversion amountConversion = saveAmountConversion();
        Assert.notNull(amountConversion, "Empty Object");
    }

    private AmountConversion saveAmountConversion() {
        ConversionResponseDto responseDto = new ConversionResponseDto();

        LocalDateTime transactionDate = LocalDateTime.now();
        responseDto.setTransactionDate(transactionDate);
        responseDto.setSourceCurrency("USD");
        responseDto.setSourceAmount(BigDecimal.TEN);
        responseDto.setTargetCurrency("TRY");
        responseDto.setTargetAmount(BigDecimal.ONE);

        return amountConversionService.saveAmountConversion(responseDto);
    }
}
