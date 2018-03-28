package com.velopayments.examples.payeeservice;

import com.velopayments.examples.authorization.AuthorizationExample;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

/**
 * Created by jt on 3/28/18.
 */
public class GetPayeesExample {

    public static void main(String[] args) throws IOException {
        String apiUrl = "https://api.sandbox.velopayments.com/v1/payees";

        //Payor ID - Unique to your account
        String payorId = "61e0690e-7d3f-4f87-8740-cf87565369d0";

        //Get API Access Token
        String apiAccessToken = AuthorizationExample.getApiToken();

        // Query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                // Add query parameter
                .queryParam("payorId", payorId);

        String apiUrlWithQueryParams = builder.toUriString();

        System.out.println("API URL with query Parameters: " + apiUrlWithQueryParams);

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

        //Call Velo API, capature JSON response as String
        String apiResponse = restTemplate.exchange(apiUrlWithQueryParams, HttpMethod.GET,
                httpEntity, String.class).getBody();

        System.out.println(apiResponse);
    }
}
