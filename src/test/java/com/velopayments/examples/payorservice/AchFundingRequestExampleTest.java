package com.velopayments.examples.payorservice;

import com.velopayments.examples.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Disabled  //api has changed, needs re-write
class AchFundingRequestExampleTest extends BaseTest {

    @BeforeEach
    void setUp() {
        super.getEnvVariables();
    }

    @Test
    void achFundingRequest() throws IOException {

        String status = AchFundingRequestExample.achFundingRequest(apiKey, apiSecret, payorId);

        assertEquals("202", status);
    }
}