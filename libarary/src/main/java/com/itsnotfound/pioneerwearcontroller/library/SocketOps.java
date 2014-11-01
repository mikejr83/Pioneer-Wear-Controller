package com.itsnotfound.pioneerwearcontroller.library;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Created by mgardner on 10/31/14.
 */
public class SocketOps {
    private String TAG = SocketOps.class.getSimpleName();

    private int port = 23;
    private String host = null;

    public SocketOps(String host, int port) {
        Log.v(TAG, "Creating SocketOps object for " + host + ":" + port);
        this.port = port;
        this.host = host;
    }

    public SocketOps(String host) {
        this(host, 23);
    }

    public void sendCommand(String command) {
        if (!command.endsWith("\r"))
            command = command + "\r";

        Log.d(TAG, "Running command: " + command);

        ClientThread ct = new ClientThread(this.host, this.port);

        Thread t = new Thread(ct);
        t.start();

        int i = 0;
        while (ct.getSocket() == null && i < 20) {
            Log.d(TAG, "Waiting on the socket.");
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                break;
            }
            i++;
        }

        Socket socket = ct.getSocket();

        if (socket == null) {
            Log.w(TAG, "No socket. Stopping.");
            return;
        }

        byte[] commandByte = new byte[0];
        try {
            commandByte = command.getBytes("ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            Log.d(TAG, "Writing command to output stream.");
            OutputStream streamOutput = socket.getOutputStream();
            streamOutput.write(commandByte);
            streamOutput.close();
        } catch (IOException e) {
            Log.e(TAG, "Unable to write command to output stream.", e);
            e.printStackTrace();
        }

//        StringBuilder builder = new StringBuilder();

        /*try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        ct.stop();

        if (!socket.isClosed())
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        ;

    }

    public class ClientThread implements Runnable {
        private String TAG = ClientThread.class.getSimpleName();

        private Socket socket;
        private String host;
        private int port;
        private boolean connected = false;

        public ClientThread(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public Socket getSocket() {
            return this.socket;
        }

        public boolean isConnected() {
            return this.connected;
        }

        @Override
        public void run() {
            Log.d(TAG, "Running!");
            try {
                this.socket = new Socket(this.host, this.port);

                InputStream streamInput = this.socket.getInputStream();
                this.connected = true;

                byte[] arrayOfByte = new byte[10000];
                while (this.connected) {
                    int j = 0;
                    try {
                        int i = arrayOfByte.length;
                        j = streamInput.read(arrayOfByte, 0, i);
                        if (j == -1) {
                            throw new Exception("Error while reading socket.");
                        }
                    } catch (Exception e0) {
                        String strException = e0.getMessage();
                        Log.e(TAG, "Error reading socket.", e0);
                        if (strException.indexOf("reset") != -1 || strException.indexOf("rejected") != -1) {
                            this.connected = false;
                            try {
                                this.socket.close();
                            } catch (IOException e1) {
                                Log.e(TAG, "Inner exception", e1);
                                e1.printStackTrace();
                            }
                            this.socket = null;
                            break;
                        }
                    }

                    if (j == 0)
                        continue;

                    final String strData = new String(arrayOfByte, 0, j).replace("\r", "");

                    Log.d(TAG, strData);
                }

                this.socket.close();
            } catch (Exception e) {
                Log.e(TAG, "Error in the client thread.", e);
                this.connected = false;
            }
        }

        public void stop() {
            this.connected = false;
            if (this.socket != null) {
                if(!this.socket.isClosed())
                    try {
                        this.socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
this.socket = null;
            }
        }
    }


}
