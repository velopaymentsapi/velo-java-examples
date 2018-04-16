package com.velopayments.examples.payeeservice;

import com.velopayments.examples.BaseTest;
import com.velopayments.examples.authorization.AuthorizationExample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetPayeesExampleTest extends BaseTest {

    @BeforeEach
    void setUp() {
        super.getEnvVariables();
    }

    @Test
    void getPayees() throws Exception {

        String payees = GetPayeesExample.getPayees(apiKey, apiSecret, payorId);

        assertNotNull(payees);

    }
}