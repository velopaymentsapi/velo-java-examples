package com.velopayments.examples.fundingmanager;

import com.velopayments.examples.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GetFundingAccountsExampleTest extends BaseTest {

    @BeforeEach
    void setUp() {
        super.getEnvVariables();
    }

    @Test
    void getFundingAccounts() throws IOException {
        String fundingAccounts = GetFundingAccountsExample.getFundingAccounts(apiKey, apiSecret, payorId);

        assertNotNull(fundingAccounts);
    }
}