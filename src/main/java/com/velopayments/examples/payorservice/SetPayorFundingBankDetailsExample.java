package com.velopayments.examples.payorservice;

import com.velopayments.examples.authorization.AuthorizationExample;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

/**
 * Created by jt on 3/29/18.
 */
public class SetPayorFundingBankDetailsExample {


    public static void main(String[] args) throws IOException {

        setFundingBankDetails(args[0], args[1], args[3]);
    }

    public static void setFundingBankDetails(String apiKey, String apiSecret, String payorId) throws IOException {

        String apiUrl = "https://api.sandbox.velopayments.com/v1/payors/{payorId}/payorFundingBankDetailsUpdate";

        //Payor ID - Unique to your account
        //String payorId = "61e0690e-7d3f-4f87-8740-cf87565369d0";

        //Get API Access Token
        String apiAccessToken = AuthorizationExample.getApiToken(apiKey, apiSecret);

        // Query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                // Add query parameter
                .queryParam("payorId", payorId);

        String apiUrlWithQueryParams = builder.toUriString();

        System.out.println("API URL with query Parameters: " + apiUrlWithQueryParams);
    }
}
