package com.velopayments.examples.addpayee;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;

/**
 * Created by jt on 3/27/18.
 */
public class AddPayeeExample {

    public static void main(String[] args) throws IOException {

                            //API Key : Secret Key
        String authString = "ebad5417-afaa-412f-9391-77dc48859351:26e3b9b0-5325-4712-8a45-376c3350b3bf";
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());

        System.out.println("Base64 Encoded Auth String is: " + encodedAuthString);

        //set auth header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + encodedAuthString);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        String authResponse = restTemplate.postForObject("https://api.sandbox.velopayments.com/oauth/token?grant_type=client_credentials",
                                    httpEntity, String.class);

        System.out.println("Auth Response is: " + authResponse);

        //read json object
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(authResponse);

        System.out.println("Access Token is: " + jsonNode.findValue("access_token").asText());

        //todo - create post for payee...
    }
}
