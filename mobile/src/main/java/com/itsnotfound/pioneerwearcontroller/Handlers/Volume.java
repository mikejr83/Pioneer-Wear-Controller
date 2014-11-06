package com.itsnotfound.pioneerwearcontroller.Handlers;

import android.util.Log;

import com.itsnotfound.pioneerwearcontroller.library.CommandOps;

/**
 * Created by mgardner on 11/1/14.
 */
public class Volume {
    private static String TAG = Volume.class.getSimpleName();

    public static void volumeDown(String ipAddress) {
        Log.d(TAG, "Sending volume down command.");
        CommandOps ops = new CommandOps(ipAddress);
        ops.sendCommand("VD");
    }

    public static void volumeUp(String ipAddress) {
        Log.d(TAG, "Sending volume up command.");
        CommandOps ops = new CommandOps(ipAddress);
        ops.sendCommand("VU");
    }
}
