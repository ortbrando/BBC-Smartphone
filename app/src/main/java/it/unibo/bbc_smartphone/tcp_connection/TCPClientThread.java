package it.unibo.bbc_smartphone.tcp_connection;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

import it.unibo.bbc_smartphone.activity.MainActivity;

/**
 * Created by brando on 04/06/2015.
 */
public class TCPClientThread extends Thread {
    private BufferedReader inFromServer;
    private MainActivity.TCPConnectionHandler tcpConnectionHandler;
    public TCPClientThread(BufferedReader inFromServer, MainActivity.TCPConnectionHandler tcpConnectionHandler){
        this.inFromServer = inFromServer;
        this.tcpConnectionHandler = tcpConnectionHandler;
    }

    @Override
    public void run() {
        while (true){
            try {
                String s = inFromServer.readLine();
                this.tcpConnectionHandler.obtainMessage(1, new JSONObject(s)).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
