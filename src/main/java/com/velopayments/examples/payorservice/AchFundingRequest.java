package com.velopayments.examples.payorservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velopayments.examples.authorization.AuthorizationExample;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jt on 3/29/18.
 */
public class AchFundingRequest {

    public static void main(String[] args) throws IOException {

        achFundingRequest(args[0], args[1], args[3]);

    }

    private static void achFundingRequest(String apiKey, String apiSecret, String payorId) throws IOException {
        String apiUrl = "https://api.sandbox.velopayments.com/v1/payors/{payorId}/achFundingRequest/";

        //Payor ID - Unique to your account
       // String payorId = "61e0690e-7d3f-4f87-8740-cf87565369d0";

        //Get API Access Token
        String apiAccessToken = AuthorizationExample.getApiToken(apiKey, apiSecret);

        // add path parameter Payor Id to URL
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl);
        String apiUrlWithPayorId = builder.build(payorId).toString();

        System.out.println("API URL is: " + apiUrlWithPayorId);

        Map<String, Object> fundingRequest = new HashMap<>();

        fundingRequest.put("amount", BigDecimal.valueOf(19.90));

        //create json object
        ObjectMapper objectMapper = new ObjectMapper();
        String fundingRequestJson = objectMapper.writeValueAsString(fundingRequest);

        System.out.println("Reqeust Body:" + fundingRequestJson);

        //Set auth header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + apiAccessToken);
        httpHeaders.add("Content-Type", "application/json");

        //add request body and http headers
        HttpEntity<String> httpEntity = new HttpEntity<>(fundingRequestJson, httpHeaders);

        //Create Spring RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        //Using Apache HTTPClient for clear debug logging (this step is optional)
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<String> responseEntity =restTemplate.exchange(apiUrlWithPayorId,
                HttpMethod.POST, httpEntity, String.class);

        System.out.println("HTTP Status Received: " + responseEntity.getStatusCode().toString());

        /**
         * If Funding account has not been setup, you will see:
         *
         *  [{"field":"Payor funding information","rejectedValue":"",
         *  "errorMessage":"Payor funding information is missing: [Property [accountNumber] in type
         *  com.velopayments.fundingmanager.event.api.LogicalFundingRequested Account number is mandatory,
         *  Property [payorName] in type com.velopayments.fundingmanager.event.api.LogicalFundingRequested
         *  Payor name is mandatory, Property [routingNumber] in type
         *  com.velopayments.fundingmanager.event.api.LogicalFundingRequested Routing number is mandatory]"}]"
         *
         *  See SetPayorFundingBankDetailsExample to set Funding Account Details.
         */
    }
}
