package it.unibo.bbc_smartphone.tcp_connection;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import it.unibo.bbc_smartphone.ParserUtils;
import it.unibo.bbc_smartphone.activity.MainActivity;
import it.unibo.bbc_smartphone.model.Alert;

public class TCPConnection extends Thread {
    private DataOutputStream outToServer;
    private MainActivity.TCPConnectionHandler tcpConnectionHandler;
    private static final String serverIP = "192.168.43.221";
    private static final int port = 6789;

    public TCPConnection(MainActivity.TCPConnectionHandler tcpConnectionHandler) {
        this.tcpConnectionHandler = tcpConnectionHandler;
    }

    @Override
    public void run(){
        Socket clientSocket;
        try {
            clientSocket = new Socket(serverIP, port);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            TCPClientThread tcpClientThread = new TCPClientThread(inFromServer, tcpConnectionHandler);
            tcpClientThread.start();
            this.sendInitialisationMsg();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendPositionToServer(double latitude, double longitude, int idPlayer) throws JSONException, IOException {
        JSONObject jsonObject = ParserUtils.getPositionToSend(latitude, longitude,idPlayer);
        String stringToSend = jsonObject.toString() + '\n';
        outToServer.writeBytes(stringToSend);
    }


    public void sendConfirmRefuseCooperationToServer(String toSend, int idPlayer) throws JSONException, IOException {
        JSONObject jsonObject = ParserUtils.getConfirmOrRefuseCooperationMsg(toSend, idPlayer);
        String stringToSend = jsonObject.toString() + '\n';
        outToServer.writeBytes(stringToSend);
    }






    public void sendInitialisationMsg() throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", 0);
        String stringToSend = jsonObject.toString() + '\n';
        Log.i("SENDINIT", stringToSend);
        outToServer.writeBytes(stringToSend);
    }

    public void sendAlertToServer(Alert alert) throws JSONException, IOException {
        JSONObject jsonObject = ParserUtils.getAlertToSend(alert);
        String stringToSend = jsonObject.toString() + '\n';
        outToServer.writeBytes(stringToSend);
    }
}