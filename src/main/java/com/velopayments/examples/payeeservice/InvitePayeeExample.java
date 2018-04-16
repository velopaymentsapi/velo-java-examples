package com.velopayments.examples.payeeservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velopayments.examples.authorization.AuthorizationExample;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

/**
 * Created by jt on 3/27/18.
 */
public class InvitePayeeExample {

    public static void main(String[] args) throws IOException {

        invitePayee(args[0], args[1], args[2]);

    }

    public static String invitePayee(String apiKey, String apiSecret, String payorId) throws IOException {
        String apiUrl = "https://api.sandbox.velopayments.com/v1/payees";

        //Get API Access Token
        String apiAccessToken = AuthorizationExample.getApiToken(apiKey, apiSecret);

        Map<String, Object> invitePayeeRequest = new HashMap<>();
        invitePayeeRequest.put("payorId", payorId);

        List<Map<String, Object>> payeesList = new ArrayList<>();

        Map<String, Object> payeeToInvite = new HashMap<>();

        payeeToInvite.put("type", "Individual");
        payeeToInvite.put("remoteId", UUID.randomUUID().toString());
        payeeToInvite.put("email", "joe@example.com");

        Map<String, Object> payeeToInviteAddress = new HashMap<>();
        payeeToInviteAddress.put("line1", "123 Main St");
        payeeToInviteAddress.put("city", "Key West");
        payeeToInviteAddress.put("zipOrPostcode", "33458");
        payeeToInviteAddress.put("country", "US");
        payeeToInvite.put("address", payeeToInviteAddress);

        Map<String, Object> individualToInvite = new HashMap<>();
        Map<String, Object> individualToInviteName = new HashMap<>();

        individualToInviteName.put("firstName", "Joe");
        individualToInviteName.put("lastName", "Buck");
        individualToInvite.put("name", individualToInviteName);

        individualToInvite.put("nationalIdentification", "123123123");
        individualToInvite.put("dateOfBirth", "1970-05-12");

        payeeToInvite.put("individual", individualToInvite);

        payeesList.add(payeeToInvite);
        invitePayeeRequest.put("payees", payeesList);

        //read json object
        ObjectMapper objectMapper = new ObjectMapper();

        //Set auth header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + apiAccessToken);
        httpHeaders.add("Content-Type", "application/json");

        //add request body and http headers
        HttpEntity<String> httpEntity = new HttpEntity<>(objectMapper.writeValueAsString(invitePayeeRequest), httpHeaders);

        //Create Spring RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        //Using Apache HTTPClient for clear debug logging (this step is optional)
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        //Call Velo API, capture JSON response as String
        String apiResponse = restTemplate.postForObject(apiUrl,
                httpEntity, String.class);

        return apiResponse;

    }
}
