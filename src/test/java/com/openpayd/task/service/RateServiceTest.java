package com.openpayd.task.service;

import com.openpayd.task.dto.ApiData;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;

@SpringBootTest
public class RateServiceTest {

    @Autowired
    RateService rateService;

    @Test
    public void apiDataUSDTRYTest() {
        BigDecimal apiDataResult = rateService.getApiDataResult("USD", "TRY");
        Assert.notNull(apiDataResult, "Result Empty");
    }

    @Test
    public void apiDataEURTRYTest() {
        BigDecimal apiDataResult = rateService.getApiDataResult("EUR", "TRY");
        Assert.notNull(apiDataResult, "Result Empty");
    }

    @Test
    public void apiDataUSDEURTest() {
        BigDecimal apiDataResult = rateService.getApiDataResult("USD", "EUR");
        Assert.notNull(apiDataResult, "Result Empty");
    }

    @Test
    public void apiDataTRYUSDTest() {
        BigDecimal apiDataResult = rateService.getApiDataResult("TRY", "USD");
        Assert.notNull(apiDataResult, "Result Empty");
    }

    @Test
    public void generateApiDataFromResponse() throws IOException {

        ClassPathResource classPathResource = new ClassPathResource("file/SampleResponse.txt");
        File file = classPathResource.getFile();
        String sampleResponse = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
        ApiData apiData = rateService.generateApiDataFromResponse(sampleResponse);
        Assert.notNull(apiData, "Empty Object");
    }
}
