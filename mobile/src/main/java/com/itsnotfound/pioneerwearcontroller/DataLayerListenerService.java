package com.itsnotfound.pioneerwearcontroller;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;
import com.itsnotfound.pioneerwearcontroller.Handlers.Power;
import com.itsnotfound.pioneerwearcontroller.Handlers.Volume;
import com.itsnotfound.pioneerwearcontroller.library.ControlMessages;

/**
 * Created by mgardner on 10/30/14.
 */
public class DataLayerListenerService extends WearableListenerService {

    private String TAG = DataLayerListenerService.class.getSimpleName();

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        String ipAddress =
                PreferenceManager.getDefaultSharedPreferences(this).getString("setting_ip_address", "");

        Log.d(TAG, "IP Address: " + ipAddress);
        Log.d(TAG, "Path: " + messageEvent.getPath());

        if (ipAddress == null || ipAddress.isEmpty()) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(settingsIntent);
            return;
        }

        if (messageEvent.getPath().equals(ControlMessages.CONTROL_POWER_ON)) {
            Log.d(TAG, "Running the power on command.");
            if (!Power.isPoweredOn(ipAddress))
                Power.powerOn(ipAddress);
        } else if (messageEvent.getPath().equals(ControlMessages.CONTROL_POWER_OFF)) {

        } else if (messageEvent.getPath().equals(ControlMessages.CONTROL_VOLUME_DOWN)) {
            Log.d(TAG, "Running the volume down command.");
            Volume.volumeDown(ipAddress);
        } else if (messageEvent.getPath().equals(ControlMessages.CONTROL_VOLUME_UP)) {
            Log.d(TAG, "Running the volume up command.");
            Volume.volumeUp(ipAddress);
        } else{
            Log.d(TAG, "Command not found: " + messageEvent.getPath());
        }
    }

    @Override
    public void onPeerConnected(Node peer) {

        Log.d(TAG, "Peer connected! " + peer.getDisplayName());
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        Log.d(TAG, "Peer disconnected! " + peer.getDisplayName());
    }
}
