package org.pineapple.core;

public class UserData
{
    private String emailAddress;
    private String securityToken;

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
}
