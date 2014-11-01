package com.itsnotfound.pioneerwearcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.itsnotfound.pioneerwearcontroller.library.ControlMessages;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks {
    private String TAG = MainActivity.class.getSimpleName();
    private GoogleApiClient googleApiClient;

    private View.OnClickListener powerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "Power button clicked!");

            if (!googleApiClient.isConnected())
                Log.d(TAG, "GoogleApiClient isn't connected.");

            Wearable.NodeApi.getConnectedNodes(googleApiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                @Override
                public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                    Log.d(TAG, "Got node result");
                    if (getConnectedNodesResult == null || getConnectedNodesResult.getNodes().size() == 0)
                        return;

                    Node node = getConnectedNodesResult.getNodes().get(0);

                    Log.d(TAG, "Got node; sending message.");
                    Wearable.MessageApi.sendMessage(googleApiClient,
                            node.getId(),
                            ControlMessages.CONTROL_POWER_ON,
                            null);
                }
            });
        }
    };

    private View.OnClickListener volumeDownListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "Volume down clicked!");

            if (!googleApiClient.isConnected())
                Log.d(TAG, "GoogleApiClient isn't connected.");

            Wearable.NodeApi.getConnectedNodes(googleApiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                @Override
                public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                    Log.d(TAG, "Got node result");
                    if (getConnectedNodesResult == null || getConnectedNodesResult.getNodes().size() == 0)
                        return;

                    Node node = getConnectedNodesResult.getNodes().get(0);

                    Log.d(TAG, "Got node; sending message.");
                    Wearable.MessageApi.sendMessage(googleApiClient,
                            node.getId(),
                            ControlMessages.CONTROL_VOLUME_DOWN,
                            null);
                }
            });
        }
    };

    private View.OnClickListener volumeUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "Volume up button clicked!");

            if (!googleApiClient.isConnected())
                Log.d(TAG, "GoogleApiClient isn't connected.");

            Wearable.NodeApi.getConnectedNodes(googleApiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                @Override
                public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                    Log.d(TAG, "Got node result");
                    if (getConnectedNodesResult == null || getConnectedNodesResult.getNodes().size() == 0)
                        return;

                    Node node = getConnectedNodesResult.getNodes().get(0);

                    Log.d(TAG, "Got node; sending message.");
                    Wearable.MessageApi.sendMessage(googleApiClient,
                            node.getId(),
                            ControlMessages.CONTROL_VOLUME_UP,
                            null);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .build();

        googleApiClient.connect();

        this.setupClickListeners();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "GoogleApiClient is now connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "GoogleApiClient connection suspended: " + i);
    }

    private void setupClickListeners() {
        View powerButtonView = findViewById(R.id.button_power);
        if (powerButtonView != null)
            powerButtonView.setOnClickListener(this.powerListener);

        View volumeDownView = findViewById(R.id.button_volume_down);
        if (volumeDownView != null)
            volumeDownView.setOnClickListener(this.volumeDownListener);

        View volumeUpView = findViewById(R.id.button_volume_up);
        if (volumeUpView != null)
            volumeUpView.setOnClickListener(this.volumeUpListener);
    }
}
