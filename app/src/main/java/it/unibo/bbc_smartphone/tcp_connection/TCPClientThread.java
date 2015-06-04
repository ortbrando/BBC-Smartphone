package it.unibo.bbc_smartphone.tcp_connection;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by brando on 04/06/2015.
 */
public class TCPClientThread extends Thread {
    private BufferedReader inFromServer;
    public TCPClientThread(BufferedReader inFromServer){
        this.inFromServer = inFromServer;
    }

    @Override
    public void run() {
        while (true){
            try {
                inFromServer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
