package it.unibo.bbc_smartphone.tcp_connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by brando on 04/06/2015.
 */
public class TCPConnection {
    private DataOutputStream outToServer;
    private static final String serverIP = "localhost";
    private static final int port = 6789;

    public TCPConnection() throws IOException {
        Socket clientSocket = new Socket(serverIP, port);
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        TCPClientThread tcpClientThread = new TCPClientThread(inFromServer);
        tcpClientThread.start();
    }

    public void sendToServer(String s) throws IOException {
        outToServer.writeBytes(s);
    }
}

