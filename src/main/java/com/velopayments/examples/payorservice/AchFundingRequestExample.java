/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.velopayments.examples.payorservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velopayments.api.ApacheHttpClient;
import com.velopayments.api.HttpClient;
import com.velopayments.examples.authentication.AuthenticationExample;
import com.velopayments.examples.fundingmanager.GetFundingAccountsExample;
import com.velopayments.examples.fundingmanager.GetSourceAccountsExample;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class AchFundingRequestExample {

    /**
     * Usage - parameter 1 = Velo API Key
     *         parameter 2 = Velo API Secret
     *         parameter 3 = Payor ID (UUID)
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        achFundingRequest(args[0], args[1], args[3]);
    }

    public static String achFundingRequest(String apiKey, String apiSecret, String payorId) throws IOException {
        return achFundingRequest(apiKey, apiSecret, payorId, new ApacheHttpClient());
    }

    public static String achFundingRequest(String apiKey, String apiSecret, String payorId, HttpClient httpClient) throws IOException {
        String apiUrl = "https://api.sandbox.velopayments.com/v3/sourceAccounts/";
        String apiAction = "/fundingRequest";

        //Get API Access Token
        String apiAccessToken = AuthenticationExample.getApiToken(apiKey, apiSecret);

        //get funding account name
        String fundingAccountsResponse = GetFundingAccountsExample.getFundingAccounts(apiAccessToken, payorId);

        ObjectMapper fundingObjectMapper = new ObjectMapper();
        JsonNode fundingJsonNode = fundingObjectMapper.readValue(fundingAccountsResponse, JsonNode.class);
        System.out.println(fundingJsonNode);
        String fundingAccountId = fundingJsonNode.get("content").iterator().next().get("id").asText();

        //get source account id
        String sourceAccountsResponse = GetSourceAccountsExample.getSourceAccounts(apiAccessToken, payorId);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readValue(sourceAccountsResponse, JsonNode.class);
        String sourceAccountId = jsonNode.get("content").iterator().next().get("id").asText();

        // add path parameter Payor Id to URL
        String apiUrlWithPayorId = apiUrl + sourceAccountId + apiAction;

        Map<String, Object> fundingRequest = new HashMap<>();

        fundingRequest.put("fundingAccountId", fundingAccountId);
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
