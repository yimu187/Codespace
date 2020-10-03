package com.openpayd.task.service;

import com.openpayd.task.configuration.ApplicationContextHolder;
import com.openpayd.task.dto.ApiData;
import com.openpayd.task.dto.Rate;
import com.openpayd.task.excption.OpedPayDAccessException;
import com.openpayd.task.excption.OpenPayDException;
import com.openpayd.task.excption.OpenPaydClientErrorException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Service
public class RateService {

    public BigDecimal getApiDataResult(String sourceCurrency, String targetCurrency){
        Environment environment = ApplicationContextHolder.getApplicationContext().getBean(Environment.class);
        String url = environment.getProperty("com.openpayd.task.api.url");
        String accessKey = environment.getProperty("com.openpayd.task.api.accessKey");
        url = url + "?access_key=" + accessKey;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ApiData apiData;
        try{
            String response = restTemplate.getForObject(url, String.class);
            apiData = generateApiDataFromResponse(response);
        }catch (ResourceAccessException conn){
            throw new OpedPayDAccessException("Can not connect to API.");
        }catch (HttpClientErrorException clientErrEx){
            throw new OpenPaydClientErrorException(clientErrEx.getMessage());
        }catch (JSONException in){
            throw new OpenPaydClientErrorException("Invalid JSON");
        }catch (Exception ex){
            throw new OpenPayDException(ex);
        }

        List<Rate> rates = apiData.getRates();

        BigDecimal sourceRateValue = BigDecimal.ONE;
        if(!sourceCurrency.equals("EUR")){
            Optional<Rate> optSourceCurrency = filterCurrencyRate(sourceCurrency, rates);
            if(!optSourceCurrency.isPresent()){
                throw new OpenPayDException("There is no currency for " + sourceCurrency);
            }
            Rate sourceRate = optSourceCurrency.get();
            sourceRateValue = sourceRate.getValue();
        }

        BigDecimal targetRateValue = BigDecimal.ONE;
        if(!targetCurrency.equals("EUR")){
            Optional<Rate> optTargetCurrency = filterCurrencyRate(targetCurrency, rates);
            if(!optTargetCurrency.isPresent()){
                throw new OpenPayDException("There is no currency for " + sourceCurrency);
            }
            Rate targetRate = optTargetCurrency.get();
            targetRateValue = targetRate.getValue();
        }

        BigDecimal result = targetRateValue.divide(sourceRateValue, 6, RoundingMode.HALF_UP);

        return result;
    }

    private Optional<Rate> filterCurrencyRate(String sourceCurrency, List<Rate> rates) {
        return rates.stream().filter(rate -> rate.getCurrency().equals(sourceCurrency)).findFirst();
    }

    public ApiData generateApiDataFromResponse(String response) {
        JSONObject obj = new JSONObject(response);
        String base = obj.getString("base");
        String dateFromJson = obj.getString("date");
        LocalDate date = LocalDate.parse(dateFromJson);

        JSONObject rates = obj.getJSONObject("rates");
        Iterator keys = rates.keys();
        ApiData apiData = new ApiData();
        apiData.setBase(base);
        apiData.setDate(date);
        while(keys.hasNext()){
            Rate rate = new Rate();
            String currency = (String)keys.next();
            BigDecimal value = rates.getBigDecimal(currency);
            value = value.setScale(5, RoundingMode.HALF_UP);
            rate.setCurrency(currency);
            rate.setValue(value);
            apiData.getRates().add(rate);
        }
        return apiData;
    }
}
