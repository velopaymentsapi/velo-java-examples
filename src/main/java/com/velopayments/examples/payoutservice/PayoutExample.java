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
package com.velopayments.examples.payoutservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velopayments.api.ApacheHttpClient;
import com.velopayments.api.HttpClient;
import com.velopayments.examples.authentication.AuthenticationExample;
import com.velopayments.examples.payeeservice.GetPayeesExample;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class PayoutExample {

    public static final String PAYOUT_URL = "https://api.sandbox.velopayments.com/v3/payouts";

    /**
     * Usage - parameter 1 = Velo API Key
     *         parameter 2 = Velo API Secret
     *         parameter 3 = Payor ID (UUID)
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        doPayout(args[0], args[1], args[3]);
    }

    public static String doPayout(String apiKey, String apiSecret, String payorId) throws Exception {
        return doPayout( apiKey,  apiSecret, payorId, new ApacheHttpClient());
    }

    public static String doPayout(String apiKey, String apiSecret, String payorId, HttpClient httpClient) throws Exception {

        //Get API Access Token
        String apiAccessToken = AuthenticationExample.getApiToken(apiKey, apiSecret);

        String getPayeesReponse = GetPayeesExample.getPayees(apiAccessToken, payorId);

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode getPayeesJson = objectMapper.readValue(getPayeesReponse, JsonNode.class);

        List<String> payeeRemoteIds = new ArrayList<>();

        getPayeesJson.get("content").iterator().forEachRemaining(payeeNode -> {
            payeeRemoteIds.add(payeeNode.get("payorRefs").iterator().next().get("remoteId").asText());
        });

        Map<String, Object> payoutRequest = new HashMap<>();
        payoutRequest.put("payorId", payorId);
        payoutRequest.put("memo", "API Example Test");
        payoutRequest.put("payments", new ArrayList<>());
        
        payeeRemoteIds.forEach(payeeRemoteId -> {
            Map<String, Object> paymentObj = new HashMap<>();
            paymentObj.put("remoteId", payeeRemoteId);
            paymentObj.put("currency", "USD");
            paymentObj.put("amount", new BigDecimal("1.25"));
            paymentObj.put("memo", "Test Payment - API Example");
            paymentObj.put("sourceAccountName", "Test1");
            paymentObj.put("payorPaymentId", UUID.randomUUID().toString());
            
            ((List) payoutRequest.get("payments")).add(paymentObj);
            
        });

        String payoutRequestJson = objectMapper.writeValueAsString(payoutRequest);

        //Set auth header
        Collection<HttpClient.HttpHeader> httpHeaders = Collections.checkedList(new LinkedList<>(), HttpClient.HttpHeader.class);
        httpHeaders.add(new HttpClient.HttpHeader("Authorization", "Bearer " + apiAccessToken));
        httpHeaders.add(new HttpClient.HttpHeader("Content-Type", "application/json"));

        HttpClient.HttpResponse submitPayoutResponse = httpClient.post(PAYOUT_URL, httpHeaders, payoutRequestJson, HttpClient.ContentType.JSON);
        // get payoutId from submit response
        JsonNode submitPayoutJsonNode = objectMapper.readValue(submitPayoutResponse.getBody(), JsonNode.class);

        String payoutId = submitPayoutJsonNode.get("payoutId").asText();

        System.out.println("Submitted Payout Id: " + payoutId);

        //Poll payout for ACCEPTED Status
        for(int i = 0; i < 6; i++) {
            //Pause for 5 seconds
            Thread.sleep(5000);

            HttpClient.HttpResponse getPayoutResponse = httpClient.get(PAYOUT_URL + "/" + payoutId, httpHeaders);
            JsonNode getPayoutJsonNode = objectMapper.readValue(getPayoutResponse.getBody(), JsonNode.class);
            String payoutStatus = getPayoutJsonNode.get("status").asText();
            if (payoutStatus.equals("ACCEPTED")) {break; }
            if (i >= 5) {
                return String.valueOf(getPayoutResponse.getCode());
            }

        }
        //Quote Payout
        System.out.println("Quote Payout Id: " + payoutId);
        HttpClient.HttpResponse quotePayoutResonse = httpClient.post(PAYOUT_URL + "/" + payoutId + "/quote", httpHeaders, null, HttpClient.ContentType.JSON);
        JsonNode getPayoutJsonNode = objectMapper.readValue(quotePayoutResonse.getBody(), JsonNode.class);

        //Instruct Payout
        System.out.println("Instruct Payout Id: " + payoutId);

        HttpClient.HttpResponse instructPayoutResonse = httpClient.post(PAYOUT_URL + "/" + payoutId, httpHeaders, null, HttpClient.ContentType.JSON);

        return String.valueOf(instructPayoutResonse.getCode());
    }

}
