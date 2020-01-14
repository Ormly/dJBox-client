package org.pineapple.core;

/**
 * Represents a Jukebox name-ip pair, which is curated by persistent storage.
 */
public class JukeBoxIPNamePair
{
    private String jukeBoxName;
    private String jukeBoxIP;

    /**
     * Generates a new JukeBox - IP pair.
     * @param jukeBoxName
     * @param jukeBoxIP
     */
    public JukeBoxIPNamePair(String jukeBoxName, String jukeBoxIP)
    {
        this.jukeBoxName = jukeBoxName;
        this.jukeBoxIP = jukeBoxIP;
    }

    /**
     * Sets the jukeBox name.
     * @param jukeBoxName
     */
    public void setJukeBoxName(String jukeBoxName)
    {
        this.jukeBoxName = jukeBoxName;
    }

    /**
     * Sets the jukeBox IP.
     * @param jukeBoxIP
     */
    public void setJukeBoxIP(String jukeBoxIP)
    {
        this.jukeBoxIP = jukeBoxIP;
    }

    /**
     * Returns the name of the jukeBox.
     * @return String jukeBoxName
     */
    public String getJukeBoxName()
    {
        return jukeBoxName;
    }

    /**
     * Returns the jukeBox IP address.
     * @return String jukeBoxIP
     */
    public String getJukeBoxIP()
    {
        return jukeBoxIP;
    }
}
