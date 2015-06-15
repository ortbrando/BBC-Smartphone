package it.unibo.bbc_smartphone.tcp_connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import it.unibo.bbc_smartphone.activity.MainActivity;

/**
 * Created by brando on 04/06/2015.
 */
public class TCPConnection extends Thread {
    private DataOutputStream outToServer;
    private MainActivity.TCPConnectionHandler tcpConnectionHandler;
    private static final String serverIP = "192.168.2.104";
    private static final int port = 6789;

    public TCPConnection(MainActivity.TCPConnectionHandler tcpConnectionHandler) {
        this.tcpConnectionHandler = tcpConnectionHandler;
    }

    @Override
    public void run(){
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(serverIP, port);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            TCPClientThread tcpClientThread = new TCPClientThread(inFromServer, tcpConnectionHandler);
            tcpClientThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToServer(String s) throws IOException {
        if(outToServer!=null){
            outToServer.writeBytes(s);
        }
    }
}

