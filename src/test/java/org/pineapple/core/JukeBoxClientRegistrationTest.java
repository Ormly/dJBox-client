package org.pineapple.core;

import org.junit.jupiter.api.*;

import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JukeBoxClient Registration Test - testing API calls and internal client logic")
public class JukeBoxClientRegistrationTest
{
    private static JukeBoxClient jukeBoxClient;
    private static Logger log = Logger.getLogger(JukeBoxClientRegistrationTest.class.getName());

    @Test
    @BeforeAll
    static void setup()
    {
        jukeBoxClient = jukeBoxClient.getJukeBoxClientInstance();
        assertNotNull(jukeBoxClient, "JukeBoxClient instance could not be retrieved.");

        jukeBoxClient.doConnectViaIP("http://localhost:8080");
    }

    @Test
    public void registerNewUser()
    {

        // Use timestamp as the prefix as email. This is to make sure no duplicate email is generated in test
        String timeStamp = new SimpleDateFormat("MM.dd.HH.mm").format(new java.util.Date());
        String randomEmail = timeStamp + "@test.com";

        String password = "test";

        assertEquals(ResponseState.SUCCESS,jukeBoxClient.doRegistration(randomEmail,password));
        assertEquals(ResponseState.SUCCESS,jukeBoxClient.doAuthentication(randomEmail,password));
        jukeBoxClient.doLogout();

    }

    @Test
    public void registerExistedUser()
    {
        String email = "testperson@gmail.com";
        String password = "password";

        assertEquals(ResponseState.AUTHFAIL,jukeBoxClient.doRegistration(email,password));

    }


}
