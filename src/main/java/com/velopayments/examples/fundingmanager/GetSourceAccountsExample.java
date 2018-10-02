package com.velopayments.examples.fundingmanager;

import com.velopayments.api.ApacheHttpClient;
import com.velopayments.api.HttpClient;
import com.velopayments.examples.authorization.AuthorizationExample;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;


public class GetSourceAccountsExample {

    public static void main(String[] args) throws Exception {
        getSourceAccounts(args[0], args[1], args[3]);
    }

    public static String getSourceAccounts(String apiKey, String apiSecret, String payorId) throws IOException {
        return getSourceAccounts(apiKey, apiSecret, payorId, new ApacheHttpClient());
    }

    public static String getSourceAccounts(String apiKey, String apiSecret, String payorId, HttpClient httpClient) throws IOException {
        String apiUrl = "https://api.sandbox.velopayments.com/v1/sourceAccounts";

        // Query parameters
        String apiUrlWithQueryParams = apiUrl + "/?payorId=" + payorId;

        System.out.println("API URL with query Parameters: " + apiUrlWithQueryParams);

        //Get API Access Token
        String apiAccessToken = AuthorizationExample.getApiToken(apiKey, apiSecret);
        //Set auth header
        Collection<HttpClient.HttpHeader> httpHeaders = Collections.checkedList(new LinkedList<>(), HttpClient.HttpHeader.class);
        httpHeaders.add(new HttpClient.HttpHeader("Authorization", "Bearer " + apiAccessToken));
        httpHeaders.add(new HttpClient.HttpHeader("Content-Type", "application/json"));

        HttpClient.HttpResponse apiResponse = httpClient.get(apiUrlWithQueryParams, httpHeaders);

        System.out.println(apiResponse);

        return apiResponse.getBody();
    }
}
