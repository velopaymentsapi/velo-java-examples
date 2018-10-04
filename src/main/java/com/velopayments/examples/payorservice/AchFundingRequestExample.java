package com.velopayments.examples.payorservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velopayments.api.ApacheHttpClient;
import com.velopayments.api.HttpClient;
import com.velopayments.examples.authorization.AuthorizationExample;
import com.velopayments.examples.fundingmanager.GetSourceAccountsExample;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class AchFundingRequestExample {

    public static void main(String[] args) throws IOException {
        achFundingRequest(args[0], args[1], args[3]);
    }

    public static String achFundingRequest(String apiKey, String apiSecret, String payorId) throws IOException {
        return achFundingRequest(apiKey, apiSecret, payorId, new ApacheHttpClient());
    }

    public static String achFundingRequest(String apiKey, String apiSecret, String payorId, HttpClient httpClient) throws IOException {
        String apiUrl = "https://api.sandbox.velopayments.com/v1/sourceAccounts/";
        String apiAction = "/achFundingRequest";

        //Get API Access Token
        String apiAccessToken = AuthorizationExample.getApiToken(apiKey, apiSecret);

        //get source account id
        String sourceAccountsResponse = GetSourceAccountsExample.getSourceAccounts(apiAccessToken, payorId);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readValue(sourceAccountsResponse, JsonNode.class);
        String sourceAccountId = jsonNode.get("content").iterator().next().get("id").asText();

        // add path parameter Payor Id to URL
        String apiUrlWithPayorId = apiUrl + sourceAccountId + apiAction;

        Map<String, Object> fundingRequest = new HashMap<>();

        fundingRequest.put("amount", BigDecimal.valueOf(19.90));

        //create json object
        String fundingRequestJson = objectMapper.writeValueAsString(fundingRequest);

        //Set auth header
        Collection<HttpClient.HttpHeader> httpHeaders = Collections.checkedList(new LinkedList<>(), HttpClient.HttpHeader.class);
        httpHeaders.add(new HttpClient.HttpHeader("Authorization", "Bearer " + apiAccessToken));
        httpHeaders.add(new HttpClient.HttpHeader("Content-Type", "application/json"));

        //add request body and http headers
        HttpClient.HttpResponse apiResponse = httpClient.post(apiUrlWithPayorId, httpHeaders, fundingRequestJson, HttpClient.ContentType.JSON);

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

        return String.valueOf(apiResponse.getCode());
    }
}
