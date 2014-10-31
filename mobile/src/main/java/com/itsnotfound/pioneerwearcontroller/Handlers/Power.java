package com.itsnotfound.pioneerwearcontroller.Handlers;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.IOException;

/**
 * Created by mgardner on 10/30/14.
 */
public class Power {
    public static void powerOn(String ipAddress) {
        TelnetClient telnetClient = new TelnetClient();
        try {
            telnetClient.connect(ipAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
