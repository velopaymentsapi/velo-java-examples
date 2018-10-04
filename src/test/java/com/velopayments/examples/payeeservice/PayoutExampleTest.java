package com.velopayments.examples.payeeservice;

import com.velopayments.examples.BaseTest;
import com.velopayments.examples.payoutservice.PayoutExample;
import org.apache.http.client.HttpResponseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PayoutExampleTest extends BaseTest {

    @BeforeEach
    void setUp() {
        super.getEnvVariables();
    }

    @Test
    void doPayout() throws Exception {

        assertThrows(HttpResponseException.class , () -> PayoutExample.doPayout(apiKey, apiSecret, payorId));
    }
}