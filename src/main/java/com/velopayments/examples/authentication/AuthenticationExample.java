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
package com.velopayments.examples.authentication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velopayments.api.ApacheHttpClient;
import com.velopayments.api.HttpClient;

import java.io.IOException;
import java.util.*;

public class AuthenticationExample {

    /**
     * Usage - parameter 1 = Velo API Key, parameter 2 = Velo API Secret
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        getApiToken(args[0], args[1]);
    }

    public static String getApiToken(String apiKey, String apiSecret) throws IOException {
        return getApiToken(apiKey, apiSecret, new ApacheHttpClient());
    }

    public static String getApiToken(String apiKey, String apiSecret, HttpClient httpClient) throws IOException {

        //authorization URL
        String authUrl = "https://api.sandbox.velopayments.com/v1/authenticate?grant_type=client_credentials";

        //Base64 Encode API credentials
        String authString = apiKey + ":" + apiSecret;
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());

        Collection<HttpClient.HttpHeader> httpHeaders = Collections.checkedList(new LinkedList<>(), HttpClient.HttpHeader.class);
        httpHeaders.add(new HttpClient.HttpHeader("Authorization", "Basic " + encodedAuthString));
        httpHeaders.add(new HttpClient.HttpHeader("Content-Type", "application/json"));

        HttpClient.HttpResponse authResponse  = httpClient.post(authUrl, httpHeaders, "", HttpClient.ContentType.JSON);

        System.out.println("Auth Response is: " + authResponse);

        //Read json object using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(authResponse.getBody());

        //Get Access Token from JSON response object
        String apiAccessToken = jsonNode.findValue("access_token").asText();

        return apiAccessToken;
    }
}
