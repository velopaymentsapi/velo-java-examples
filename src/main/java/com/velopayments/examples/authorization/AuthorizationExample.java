package com.velopayments.examples.authorization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velopayments.api.ApacheHttpClient;
import com.velopayments.api.HttpClient;

import java.io.IOException;
import java.util.*;

public class AuthorizationExample {

    public static void main(String[] args) throws IOException {
        getApiToken(args[0], args[1]);
    }

    public static String getApiToken(String apiKey, String apiSecret) throws IOException {
        return getApiToken(apiKey, apiSecret, new ApacheHttpClient());
    }

    public static String getApiToken(String apiKey, String apiSecret, HttpClient httpClient) throws IOException {

        //authorization URL
        String authUrl = "https://api.sandbox.velopayments.com/v1/authenticate?grant_type=client_credentials";

        //Base64 Encode API credentials
        String authString = apiKey + ":" + apiSecret;
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());

        System.out.println("Base64 Encoded Auth credentials string is: " + encodedAuthString);

        Collection<HttpClient.HttpHeader> httpHeaders = Collections.checkedList(new LinkedList<>(), HttpClient.HttpHeader.class);
        httpHeaders.add(new HttpClient.HttpHeader("Authorization", "Basic " + encodedAuthString));
        httpHeaders.add(new HttpClient.HttpHeader("Content-Type", "application/json"));

        HttpClient.HttpResponse authResponse  = httpClient.post(authUrl, httpHeaders, "", HttpClient.ContentType.JSON);

        System.out.println("Auth Response is: " + authResponse);

        //Read json object using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(authResponse.getBody());

        //Get Access Token from JSON response object
        String apiAccessToken = jsonNode.findValue("access_token").asText();

        System.out.println("Access Token is: " + apiAccessToken);

        return apiAccessToken;
    }
}
