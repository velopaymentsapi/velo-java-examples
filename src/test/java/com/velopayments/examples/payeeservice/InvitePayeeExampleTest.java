package com.velopayments.examples.payeeservice;

import com.velopayments.examples.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class InvitePayeeExampleTest extends BaseTest {

    @BeforeEach
    void setUp() {
        super.getEnvVariables();
    }

    @Test
    void invitePayee() throws IOException {

        String result = InvitePayeeExample.invitePayee(apiKey, apiSecret, payorId);

        assertNotNull(result);
    }
}