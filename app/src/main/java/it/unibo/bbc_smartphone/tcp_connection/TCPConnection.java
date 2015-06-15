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
public class TCPConnection {
    private DataOutputStream outToServer;
    private MainActivity.TCPConnectionHandler tcpConnectionHandler;
    private static final String serverIP = "10.201.8.4";
    private static final int port = 6789;

    public TCPConnection(MainActivity.TCPConnectionHandler tcpConnectionHandler) throws IOException {
        this.tcpConnectionHandler = tcpConnectionHandler;
        Socket clientSocket = new Socket(serverIP, port);
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        TCPClientThread tcpClientThread = new TCPClientThread(inFromServer, tcpConnectionHandler);
        tcpClientThread.start();
    }

    public void sendToServer(String s) throws IOException {
        outToServer.writeBytes(s);
    }
}

