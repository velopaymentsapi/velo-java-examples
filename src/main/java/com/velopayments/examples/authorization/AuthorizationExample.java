package com.velopayments.examples.authorization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;

/**
 * Created by jt on 3/28/18.
 */
public class AuthorizationExample {

    public static void main(String[] args) throws IOException {
        getApiToken(args[0], args[1]);
    }

    public static String getApiToken(String apiKey, String apiSecret) throws IOException {

        //authorization URL
        String authUrl = "https://api.sandbox.velopayments.com/oauth/token?grant_type=client_credentials";

        //Base64 Encode API credentials
        String authString = apiKey + ":" + apiSecret;
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());

        System.out.println("Base64 Encoded Auth credentials string is: " + encodedAuthString);

        //Set auth header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + encodedAuthString);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        //Create Spring RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        //Using Apache HTTPClient for clear debug logging (this step is optional)
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        //Call Velo API, capature JSON response as String
        String authResponse = restTemplate.postForObject(authUrl,
                httpEntity, String.class);

        System.out.println("Auth Response is: " + authResponse);

        //Read json object using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(authResponse);

        //Get Access Token from JSON response object
        String apiAccessToken = jsonNode.findValue("access_token").asText();

        System.out.println("Access Token is: " + apiAccessToken);

        return apiAccessToken;
    }
}
