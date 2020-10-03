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
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class RateServiceTest {

    @Autowired
    RateService rateService;

    @Test
    public void apiDataTest() {
        ApiData apiDataResult = rateService.getApiDataResult();
        Assert.notNull(apiDataResult, "Result Empty");
    }

    @Test
    public void apiDataSymbolsTest() {
        List<String> symbols = Arrays.asList("USD", "TRY");
        ApiData apiDataResult = rateService.getApiDataResult(symbols);
        Assert.notNull(apiDataResult, "Result Empty");
    }

    @Test
    public void apiDataBaseTest() {
        ApiData apiDataResult = rateService.getApiDataResult("EUR");
        Assert.notNull(apiDataResult, "Result Empty");
    }

    @Test
    public void apiDataBaseAndSymbolsTest() {
        List<String> symbols = Arrays.asList("USD", "TRY");
        ApiData apiDataResult = rateService.getApiDataResult("EUR", symbols);
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
