package com.itsnotfound.pioneerwearcontroller.Handlers;

import android.util.Log;

import com.itsnotfound.pioneerwearcontroller.library.SocketOps;

/**
 * Created by mgardner on 10/30/14.
 */
public class Power {
    private static String TAG = Power.class.getSimpleName();

    public static void powerOn(String ipAddress) {
        SocketOps ops = new SocketOps(ipAddress);
        ops.sendCommand("PO");
    }

    public static boolean isPoweredOn(String ipAddress){
        Log.d(TAG, "Checking power on.");
        SocketOps ops = new SocketOps(ipAddress);
        ops.sendCommand("?P");

        return false;
    }
}
