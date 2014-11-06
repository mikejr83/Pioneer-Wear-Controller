package com.itsnotfound.pioneerwearcontroller.library;

import android.util.Log;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.SocketException;

/**
 * Created by mgardner on 11/5/14.
 */
public class CommandOps {
    private String TAG = CommandOps.class.getSimpleName();

    private int port = 23;
    private String host = null;
    private TelnetClient telnetClient = null;
    private InputStream inputStream;
    private PrintStream outputStream;
    private StringBuffer stringBuffer;

    public CommandOps(String host, int port) {
        Log.v(TAG, "Creating CommandOps object for " + host + ":" + port);
        this.port = port;
        this.host = host;
        this.telnetClient = new TelnetClient();
        this.stringBuffer = new StringBuffer();
    }

    public CommandOps(String host) {
        this(host, 23);
    }

    public void sendCommand(final String command) {
        Log.d(TAG, "Running command: " + command);

        try {
            telnetClient.connect(this.host, this.port);
            Log.d(TAG, "Telnet connected!");
        } catch (IOException e) {
            Log.e(TAG, "Error with telnet client connecting.", e);
            return;
        }

        inputStream = telnetClient.getInputStream();
        outputStream = new PrintStream(telnetClient.getOutputStream());

        try {
            telnetClient.setKeepAlive(true);
        } catch (SocketException e) {
            Log.e(TAG, "Unable to set KeepAlive!", e);
            return;
        }

        this.executeCommand(command);
        this.disconnect();
    }

    private void executeCommand(String command) {
        this.outputStream.println(command + "\r");
        this.outputStream.flush();
        Log.d(TAG, "Command sent!");
    }

    private void disconnect() {
        Log.d(TAG, "Disconnecting");
        try {
            Log.d(TAG, "Closing input stream...");
            this.inputStream.close();
            Log.d(TAG, "InputStream closed.");
            Log.d(TAG, "Closing output stream...");
            this.outputStream.close();
            Log.d(TAG, "Output stream closed.");
            Log.d(TAG, "Disconnecting telnet client...");
            this.telnetClient.disconnect();
            Log.d(TAG, "Telnet client disconnected.");
        } catch (Exception e) {
            Log.e(TAG, "Error during disconnect.", e);
        }
    }
}
