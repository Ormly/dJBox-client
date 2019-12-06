package org.pineapple.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JukeBoxClient Test")
public class JukeBoxClientTest
{
    private static JukeBoxClient jukeBoxClient = JukeBoxClient.getJukeBoxClientInstance();

    @Test
    @DisplayName("Login")
    void loginTest()
    {
        Assertions.assertEquals((jukeBoxClient.doAuthentication("testperson@gmail.com", "password")),ResponseState.SUCCESS);
    }

    @Test
    @DisplayName("Logout")
    void logoutTest()
    {
        Assertions.assertEquals((jukeBoxClient.doLogout()),ResponseState.SUCCESS);
    }
}
