package com.velopayments.examples.payeeservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velopayments.api.ApacheHttpClient;
import com.velopayments.api.HttpClient;
import com.velopayments.examples.authorization.AuthorizationExample;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.*;

public class InvitePayeeExample {

    public static void main(String[] args) throws IOException {
        invitePayee(args[0], args[1], args[2]);
    }

    public static String invitePayee(String apiKey, String apiSecret, String payorId) throws IOException {
        return invitePayee(apiKey, apiSecret, payorId, new ApacheHttpClient());
    }

    public static String invitePayee(String apiKey, String apiSecret, String payorId , HttpClient httpClient) throws IOException {
        String apiUrl = "https://api.sandbox.velopayments.com/v2/payees";

        //random string to keep email unique
        String randomString = RandomStringUtils.randomAlphabetic(10);

        //Get API Access Token
        String apiAccessToken = AuthorizationExample.getApiToken(apiKey, apiSecret);

        Map<String, Object> invitePayeeRequest = new HashMap<>();
        invitePayeeRequest.put("payorId", payorId);

        List<Map<String, Object>> payeesList = new ArrayList<>();

        Map<String, Object> payeeToInvite = new HashMap<>();

        payeeToInvite.put("type", "Individual");
        payeeToInvite.put("remoteId", UUID.randomUUID().toString());
        payeeToInvite.put("email", "joe" + "+" + randomString + "@example.com");

        Map<String, Object> payeeToInviteAddress = new HashMap<>();
        payeeToInviteAddress.put("line1", "123 Main St");
        payeeToInviteAddress.put("city", "Key West");
        payeeToInvite.put("countyOrProvince", "FL");
        payeeToInviteAddress.put("zipOrPostcode", "33458");
        payeeToInviteAddress.put("country", "US");
        payeeToInvite.put("address", payeeToInviteAddress);

        Map<String, Object> individualToInvite = new HashMap<>();
        Map<String, Object> individualToInviteName = new HashMap<>();

        individualToInviteName.put("firstName", "Joe");
        individualToInviteName.put("lastName", "Buck" + "+" + randomString);
        individualToInvite.put("name", individualToInviteName);

        individualToInvite.put("nationalIdentification", "123123123");
        individualToInvite.put("dateOfBirth", "1970-05-12");

        payeeToInvite.put("individual", individualToInvite);

        payeesList.add(payeeToInvite);
        invitePayeeRequest.put("payees", payeesList);

        //read json object
        ObjectMapper objectMapper = new ObjectMapper();

        //Set auth header
        Collection<HttpClient.HttpHeader> httpHeaders = Collections.checkedList(new LinkedList<>(), HttpClient.HttpHeader.class);
        httpHeaders.add(new HttpClient.HttpHeader("Authorization", "Bearer " + apiAccessToken));
        httpHeaders.add(new HttpClient.HttpHeader("Content-Type", "application/json"));

        HttpClient.HttpResponse apiResponse  = httpClient.post(apiUrl, httpHeaders, objectMapper.writeValueAsString(invitePayeeRequest), HttpClient.ContentType.JSON);

        return String.valueOf(apiResponse.getCode());
    }
}
