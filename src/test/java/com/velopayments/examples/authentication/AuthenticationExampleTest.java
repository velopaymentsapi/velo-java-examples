package com.velopayments.examples.authentication;

import com.velopayments.examples.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationExampleTest extends BaseTest {

    @BeforeEach
    void setUp() {
        super.getEnvVariables();
    }

    @Test
    void getApiToken() throws IOException {

        String token = AuthenticationExample.getApiToken(apiKey, apiSecret);

        assertNotNull(token);
    }
}