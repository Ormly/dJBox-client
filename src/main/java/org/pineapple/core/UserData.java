package org.pineapple.core;

public class UserData
{
    private String emailAddress;
    private String securityToken;

    //TODO: token needs to be generated in a modular way through Authentication service

    public UserData(String emailAddress, String securityToken)
    {
        this.emailAddress = emailAddress;
        this.securityToken = securityToken;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public String getSecurityToken()
    {
        return securityToken;
    }
}
