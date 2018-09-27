package com.velopayments.examples.payorservice;

import com.velopayments.examples.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GetPayorDetailsByIdExampleTest extends BaseTest {

    @BeforeEach
    void setUp() {
        super.getEnvVariables();
    }

    @Test
    void getPayorDetails() throws IOException {
        String responsePayload = GetPayorDetailsByIdExample.getPayorDetails(apiKey, apiSecret, payorId);

        assertNotNull(responsePayload);
    }
}