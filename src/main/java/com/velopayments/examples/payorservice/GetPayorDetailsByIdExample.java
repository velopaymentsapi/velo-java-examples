package com.velopayments.examples.payorservice;

import com.velopayments.examples.authorization.AuthorizationExample;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

/**
 * Created by jt on 4/16/18.
 */
public class GetPayorDetailsByIdExample {

    public static void main(String[] args) throws IOException {
        getPayorDetails(args[0], args[1], args[3]);
    }

    public static String getPayorDetails(String apiKey, String apiSecret, String payorId) throws IOException {
        String apiUrl = "https://api.sandbox.velopayments.com/v1/payors/{payorId}";

        //Get API Access Token
        String apiAccessToken = AuthorizationExample.getApiToken(apiKey, apiSecret);

        // Path parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl);

        String apiUrlWithQueryParams = builder.build(payorId).toString();

        System.out.println("API URL with url Parameters: " + apiUrlWithQueryParams);

        //Set auth header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + apiAccessToken);
        httpHeaders.add("Content-Type", "application/json");

        //add request body and http headers
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        //Create Spring RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        //Using Apache HTTPClient for clear debug logging (this step is optional)
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrlWithQueryParams,
                HttpMethod.GET, httpEntity, String.class);

        return responseEntity.getStatusCode().toString();

    }
}
