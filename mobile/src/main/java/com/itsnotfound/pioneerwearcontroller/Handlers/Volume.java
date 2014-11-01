package com.itsnotfound.pioneerwearcontroller.Handlers;

import android.util.Log;

import com.itsnotfound.pioneerwearcontroller.library.SocketOps;

/**
 * Created by mgardner on 11/1/14.
 */
public class Volume {
    private static String TAG = Volume.class.getSimpleName();

    public static void volumeDown(String ipAddress) {
        Log.d(TAG, "Sending volume down command.");
        SocketOps ops = new SocketOps(ipAddress);
        ops.sendCommand("");
    }

    public static void volumeUp(String ipAddress) {
        Log.d(TAG, "Sending volume up command.");
        SocketOps ops = new SocketOps(ipAddress);
        ops.sendCommand("");
    }
}
