package com.velopayments.examples.payeeservice;

import com.velopayments.api.ApacheHttpClient;
import com.velopayments.api.HttpClient;
import com.velopayments.examples.authorization.AuthorizationExample;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class GetPayeesExample {

    public static void main(String[] args) throws Exception {
        getPayees(args[0], args[1], args[3]);
    }

    public static String getPayees(String apiKey, String apiSecret, String payorId) throws Exception {
        return getPayees(apiKey, apiSecret, payorId, new ApacheHttpClient());
    }

    public static String getPayees(String apiKey, String apiSecret, String payorId, HttpClient httpClient) throws Exception {

        String apiUrl = "https://api.sandbox.velopayments.com/v1/payees";

        //Get API Access Token
        String apiAccessToken = AuthorizationExample.getApiToken(apiKey, apiSecret);

        // Query parameters
        String apiUrlWithQueryParams = apiUrl + "/?payorId=" + payorId;

        System.out.println("API URL with query Parameters: " + apiUrlWithQueryParams);

        //Set auth header
        Collection<HttpClient.HttpHeader> httpHeaders = Collections.checkedList(new LinkedList<>(), HttpClient.HttpHeader.class);
        httpHeaders.add(new HttpClient.HttpHeader("Authorization", "Bearer " + apiAccessToken));
        httpHeaders.add(new HttpClient.HttpHeader("Content-Type", "application/json"));

        HttpClient.HttpResponse apiResponse = httpClient.get(apiUrlWithQueryParams, httpHeaders);

        System.out.println(apiResponse);

        return apiResponse.getBody();
    }
}
