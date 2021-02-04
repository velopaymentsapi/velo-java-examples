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
package com.velopayments.examples.payeeservice;

import com.velopayments.api.ApacheHttpClient;
import com.velopayments.api.HttpClient;
import com.velopayments.examples.authentication.AuthenticationExample;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class GetPayeesExample {

    /**
     * Usage - parameter 1 = Velo API Key
     *         parameter 2 = Velo API Secret
     *         parameter 3 = Payor ID (UUID)
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        getPayees(args[0], args[1], args[3]);
    }

    public static String getPayees(String apiKey, String apiSecret, String payorId) throws Exception {
        //Get API Access Token
        String apiAccessToken = AuthenticationExample.getApiToken(apiKey, apiSecret);
        return getPayees(apiAccessToken, payorId);
    }

    public static String getPayees(String apiAccessToken, String payorId) throws Exception {
        return getPayees(apiAccessToken, payorId, new ApacheHttpClient());
    }

    public static String getPayees(String apiAccessToken, String payorId, HttpClient httpClient) throws Exception {
        String apiUrl = "https://api.sandbox.velopayments.com/v4/payees";

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
