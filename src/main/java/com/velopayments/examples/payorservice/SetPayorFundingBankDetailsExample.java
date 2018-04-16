package com.velopayments.examples.payorservice;

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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jt on 3/29/18.
 */
public class SetPayorFundingBankDetailsExample {

    public static void main(String[] args) throws IOException {

        setFundingBankDetails(args[0], args[1], args[3]);
    }

    public static String setFundingBankDetails(String apiKey, String apiSecret, String payorId) throws IOException {

        String apiUrl = "https://api.sandbox.velopayments.com/v1/payors/{payorId}/payorFundingBankDetailsUpdate";

        //Get API Access Token
        String apiAccessToken = AuthorizationExample.getApiToken(apiKey, apiSecret);

        // Query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl);

        String apiUrlWithQueryParams = builder.build(payorId).toString();

        System.out.println("API URL with query Parameters: " + apiUrlWithQueryParams);

        Map<String, Object> updateRequest = new HashMap<>();

        updateRequest.put("routingNumber", "123456789");
        updateRequest.put("accountNumber", "7894561321259");
        updateRequest.put("accountName", "Umphrey's McGee");

        //create json object
        ObjectMapper objectMapper = new ObjectMapper();
        String updateRequestJson = objectMapper.writeValueAsString(updateRequest);

        System.out.println("Request Body: " + updateRequestJson);

        //Set auth header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + apiAccessToken);
        httpHeaders.add("Content-Type", "application/json");

        //add request body and http headers
        HttpEntity<String> httpEntity = new HttpEntity<>(updateRequestJson, httpHeaders);

        //Create Spring RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        //Using Apache HTTPClient for clear debug logging (this step is optional)
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrlWithQueryParams,
                HttpMethod.POST, httpEntity, String.class);

        return responseEntity.getStatusCode().toString();
    }
}
