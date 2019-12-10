package org.pineapple.core;

public class JukeBox
{
    private String name;
    private String ipAddress;

    public JukeBox(String name, String ipAddress)
    {
        this.name = name;
        this.ipAddress = ipAddress;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }
}
