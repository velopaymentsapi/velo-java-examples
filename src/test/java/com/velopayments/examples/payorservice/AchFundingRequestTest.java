package com.velopayments.examples.payorservice;

import com.velopayments.examples.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AchFundingRequestTest extends BaseTest {

    @BeforeEach
    void setUp() {
        super.getEnvVariables();
    }

    @Test
    void achFundingRequest() throws IOException {

        String status = AchFundingRequest.achFundingRequest(apiKey, apiSecret, payorId);

        assertEquals("202", status);
    }
}