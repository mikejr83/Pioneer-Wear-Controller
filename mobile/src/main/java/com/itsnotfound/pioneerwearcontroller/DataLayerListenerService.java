package com.itsnotfound.pioneerwearcontroller;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by mgardner on 10/30/14.
 */
public class DataLayerListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        String ipAddress =
            this.getApplicationContext().getSharedPreferences("PRIVATE", MODE_PRIVATE).getString("setting_ip_address", "");



        if (messageEvent.getPath().equals(ControlMessages.CONTROL_POWER_ON)) {

        }
    }
}
