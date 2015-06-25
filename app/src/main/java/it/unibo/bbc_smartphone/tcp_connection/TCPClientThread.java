package it.unibo.bbc_smartphone.tcp_connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

import it.unibo.bbc_smartphone.ParserUtils;
import it.unibo.bbc_smartphone.activity.MainActivity;

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
                this.receiveMessage(s);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private Object receiveMessage(String s) throws JSONException {
        JSONObject jsonObject = new JSONObject(s);
        int messageType = -1;
        Object object = null;
        switch (jsonObject.getInt("messageType")) {
            case 0:
                messageType=0;
                object = ParserUtils.getMatchFromJSONObject(jsonObject);
                break;
            case 1:
                messageType=1;
                object = ParserUtils.getTreasureChestFromJSONObject(jsonObject);
                break;
            case 2:
                messageType=2;
                object = ParserUtils.getConfirmedOrRefused(jsonObject);
                break;
            case 3:
                messageType=3;
                object = ParserUtils.getAlertFromJSONObject(jsonObject);
                break;
            case 4:
                messageType=4;
                object = ParserUtils.getMoneyTheft(jsonObject);
                break;
            case 5:
                messageType=5;
                object = ParserUtils.getNewAmount(jsonObject);
                break;
        }
        this.tcpConnectionHandler.obtainMessage(messageType, object).sendToTarget();
        return object;
    }
}
