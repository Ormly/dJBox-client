package org.pineapple.core;

/**
 * Represents the user's data, including a user's email address and their associated security token.
 */
public class UserData
{
    private String emailAddress;
    private String securityToken;
    private boolean isLoggedIn;

    /**
     * Creates a new user. By default the user is not logged in.
     */
    public UserData()
    {
        isLoggedIn = false;
    }

    /**
     * Sets the email of the user.
     * @param emailAddress
     */
    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    /**
     * Sets the token of the user.
     * @param securityToken
     */
    public void setSecurityToken(String securityToken)
    {
        this.securityToken = securityToken;
    }

    /**
     * Gets the email address of the user.
     * @return String email
     */
    public String getEmailAddress()
    {
        //TODO think about smarter return in case of null
        if(emailAddress == null)
            return "";
        else
            return emailAddress;
    }

    /**
     * Gets the token of a user.
     * @return String token
     */
    public String getSecurityToken()
    {
        if(securityToken == null)
            return "";
        else
            return securityToken;
    }

    /**
     * Clears the user object.
     */
    //TODO: make this not suck.
    public void clear()
    {
        emailAddress = null;
        securityToken = null;
        isLoggedIn = false;
    }

    /**
     * Checks if user is logged in.
     * @return boolean
     */
    public boolean isLoggedIn()
    {
        return isLoggedIn;
    }

    /**
     * Sets the user's logged in status.
     * @param value
     */
    public void setLoggedIn(boolean value)
    {
        isLoggedIn = value;
    }
}
