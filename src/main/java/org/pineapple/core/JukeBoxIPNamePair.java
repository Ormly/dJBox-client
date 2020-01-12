package org.pineapple.core;

public class JukeBoxIPNamePair
{
    private String jukeBoxName;
    private String jukeBoxIP;

    public JukeBoxIPNamePair(String jukeBoxName, String jukeBoxIP)
    {
        this.jukeBoxName = jukeBoxName;
        this.jukeBoxIP = jukeBoxIP;
    }

    public void setJukeBoxName(String jukeBoxName)
    {
        this.jukeBoxName = jukeBoxName;
    }

    public void setJukeBoxIP(String jukeBoxIP)
    {
        this.jukeBoxIP = jukeBoxIP;
    }

    public String getJukeBoxName()
    {
        return jukeBoxName;
    }

    public String getJukeBoxIP()
    {
        return jukeBoxIP;
    }
}
