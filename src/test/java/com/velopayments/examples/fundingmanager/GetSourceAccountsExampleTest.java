package com.velopayments.examples.fundingmanager;

import com.velopayments.examples.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GetSourceAccountsExampleTest extends BaseTest {

    @BeforeEach
    void setUp() {
        super.getEnvVariables();
    }

    @Test
    void getSourceAccounts() throws IOException {
        String sourceAccounts = GetSourceAccountsExample.getSourceAccounts(apiKey, apiSecret, payorId);

        System.out.println(sourceAccounts);

        assertNotNull(sourceAccounts);
    }
}