package org.pineapple.core;

/**
 * Represents the user's data, including a user's email address and their associated security token.
 */
public class UserData
{
    private String emailAddress;
    private String securityToken;
    private boolean isLoggedIn;

    public UserData()
    {
        isLoggedIn = false;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public void setSecurityToken(String securityToken)
    {
        this.securityToken = securityToken;
    }

    public String getEmailAddress()
    {
        //TODO think about smarter return in case of null
        if(emailAddress == null)
            return "";
        else
            return emailAddress;
    }

    public String getSecurityToken()
    {
        if(securityToken == null)
            return "";
        else
            return securityToken;
    }

    //TODO: make this not suck.
    public void clear()
    {
        emailAddress = null;
        securityToken = null;
        isLoggedIn = false;
    }

    public boolean isLoggedIn()
    {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean value)
    {
        isLoggedIn = value;
    }
}
