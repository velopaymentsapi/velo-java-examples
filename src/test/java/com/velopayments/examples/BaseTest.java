package com.velopayments.examples;

/**
 * Created by jt on 4/13/18.
 */
public class BaseTest {

    public String apiKey;
    public String apiSecret;
    public String payorId;

    public void getEnvVariables(){

        apiKey = System.getenv("API_KEY");
        apiSecret = System.getenv("API_SECRET");
        payorId = System.getenv("PAYOR_ID");

    }

}
