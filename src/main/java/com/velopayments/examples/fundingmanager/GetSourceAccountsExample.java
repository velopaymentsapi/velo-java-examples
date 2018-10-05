package com.velopayments.examples.fundingmanager;

import com.velopayments.api.ApacheHttpClient;
import com.velopayments.api.HttpClient;
import com.velopayments.examples.authentication.AuthenticationExample;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;


public class GetSourceAccountsExample {

    /**
     * Usage - parameter 1 = Velo API Key
     *         parameter 2 = Velo API Secret
     *         parameter 3 = Payor ID (UUID)
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        getSourceAccounts(args[0], args[1], args[3]);
    }

    public static String getSourceAccounts(String apiKey, String apiSecret, String payorId) throws IOException {
        return getSourceAccounts(apiKey, apiSecret, payorId, new ApacheHttpClient());
    }

    public static String getSourceAccounts(String apiKey, String apiSecret, String payorId, HttpClient httpClient) throws IOException {
        //Get API Access Token
        String apiAccessToken = AuthenticationExample.getApiToken(apiKey, apiSecret);

        return getSourceAccounts(apiAccessToken, payorId, httpClient);
    }

    public static String getSourceAccounts(String apiAccessToken, String payorId) throws IOException {
        return getSourceAccounts(apiAccessToken, payorId, new ApacheHttpClient());
    }

    public static String getSourceAccounts(String apiAccessToken, String payorId, HttpClient httpClient) throws IOException {
        String apiUrl = "https://api.sandbox.velopayments.com/v1/sourceAccounts";

        // Query parameters
        String apiUrlWithQueryParams = apiUrl + "/?payorId=" + payorId;

        //Set auth header
        Collection<HttpClient.HttpHeader> httpHeaders = Collections.checkedList(new LinkedList<>(), HttpClient.HttpHeader.class);
        httpHeaders.add(new HttpClient.HttpHeader("Authorization", "Bearer " + apiAccessToken));
        httpHeaders.add(new HttpClient.HttpHeader("Content-Type", "application/json"));

        HttpClient.HttpResponse apiResponse = httpClient.get(apiUrlWithQueryParams, httpHeaders);

        return apiResponse.getBody();
    }
}
