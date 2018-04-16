package com.velopayments.examples.authorization;

import com.velopayments.examples.BaseTest;
import com.velopayments.examples.payeeservice.GetPayeesExample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationExampleTest extends BaseTest {

    @BeforeEach
    void setUp() {

        super.getEnvVariables();
    }

    @Test
    void getApiToken() throws IOException {

        String token = AuthorizationExample.getApiToken(apiKey, apiSecret);

        assertNotNull(token);

    }
}