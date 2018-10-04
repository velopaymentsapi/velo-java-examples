package com.velopayments.examples.payeeservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velopayments.api.ApacheHttpClient;
import com.velopayments.api.HttpClient;
import com.velopayments.examples.authorization.AuthorizationExample;

import java.math.BigDecimal;
import java.util.*;

public class PayoutExample {

    public static final String PAYOUT_URL = "https://api.sandbox.velopayments.com/v2/payouts";

    public static void main(String[] args) throws Exception {
        doPayout(args[0], args[1], args[3]);
    }

    public static String doPayout(String apiKey, String apiSecret, String payorId) throws Exception {
        return doPayout( apiKey,  apiSecret, payorId, new ApacheHttpClient());
    }

    public static String doPayout(String apiKey, String apiSecret, String payorId, HttpClient httpClient) throws Exception {

        //Get API Access Token
        String apiAccessToken = AuthorizationExample.getApiToken(apiKey, apiSecret);

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

        //Instruct Payout
        JsonNode submitPayoutJsonNode = objectMapper.readValue(submitPayoutResponse.getBody(), JsonNode.class);

        String payoutId = submitPayoutJsonNode.get("payoutId").asText();

        System.out.println("Submitted Payout Id: " + payoutId);

        HttpClient.HttpResponse instructPayoutResonse = httpClient.post(PAYOUT_URL + "/" + payoutId, httpHeaders, payoutRequestJson, HttpClient.ContentType.JSON);

        return String.valueOf(instructPayoutResonse.getCode());
    }

}
