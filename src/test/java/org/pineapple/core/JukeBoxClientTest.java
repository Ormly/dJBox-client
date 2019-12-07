package org.pineapple.core;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JukeBoxClient Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JukeBoxClientTest
{
    private static JukeBoxClient jukeBoxClient = JukeBoxClient.getJukeBoxClientInstance();


    @Test
    @Order(1)
    @DisplayName("getJukeBoxClientInstanceTest")
    void getJukeBoxClientInstanceTest()
    {
        assertNotNull(jukeBoxClient);
    }

    @Test
    @Order(2)
    @DisplayName("doAuthenticationTest")
    void doAuthenticationTest()
    {
        assertEquals(ResponseState.SUCCESS, (jukeBoxClient.doAuthentication("testperson@gmail.com", "password")));
    }


    @Test
    @Order(99)
    @DisplayName("doLogoutTest")
    void doLogoutTest()
    {
        assertEquals(ResponseState.SUCCESS,(jukeBoxClient.doLogout()));
    }
}
