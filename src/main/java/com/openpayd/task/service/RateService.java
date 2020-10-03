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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class RateService {

    public ApiData getApiDataResult(){
        return getApiDataResult(null, new ArrayList<>());
    }

    public ApiData getApiDataResult(String base){
        return getApiDataResult(base, new ArrayList<>());
    }

    public ApiData getApiDataResult(List<String> symbols){
        return getApiDataResult(null, symbols);
    }

    public ApiData getApiDataResult(String base, List<String> symbols){
        Environment environment = ApplicationContextHolder.getApplicationContext().getBean(Environment.class);
        String url = environment.getProperty("com.openpayd.task.api.url");

        if(StringUtils.hasText(base) || (symbols != null && !symbols.isEmpty())){
            url += "?";
            if(StringUtils.hasText(base)){
                url += "base=" + base;
                if(StringUtils.hasText(base) && symbols != null && !symbols.isEmpty()){
                    url += "&";
                }
            }
            if(symbols != null && !symbols.isEmpty()){
                String syms = String.join(",", symbols);
                url += "symbols=" + syms;
            }
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

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

        return apiData;
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
