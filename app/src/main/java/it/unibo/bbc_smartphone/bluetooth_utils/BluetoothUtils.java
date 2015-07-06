package it.unibo.bbc_smartphone.bluetooth_utils;

import android.util.Log;

import org.json.JSONException;

import it.unibo.bbc_smartphone.ParserUtils;
import it.unibo.bbc_smartphone.model.Match;
import it.unibo.bbc_smartphone.model.TreasureChest;

/**
 * Created by brando on 04/06/2015.
 */
public class BluetoothUtils {
    private static BluetoothUtils instance = new BluetoothUtils();

    public static BluetoothUtils getInstance(){
        return instance;
    }

    private BluetoothUtils(){}

    private ConnectedThread connectedThread;

    public void setConnectedThread(ConnectedThread connectedThread){
        this.connectedThread = connectedThread;
    }

    public void sendLocationToMoverio(double latitude, double longitude) throws JSONException {
        if(this.connectedThread!=null) {
            connectedThread.write(ParserUtils.getPositionToSend(latitude, longitude).toString().getBytes());
        }
    }

    public void sendMatchToMoverio(Match match) throws JSONException {
        if(this.connectedThread!=null) {
            connectedThread.write(ParserUtils.getMatchJSONObject(match).toString().getBytes());
        }
    }

    public void sendTreasureChestToMoverio(TreasureChest treasureChest) throws JSONException {
        if(this.connectedThread!=null) {
            connectedThread.write(ParserUtils.getTreasureChestJSONObject(treasureChest).toString().getBytes());
        }
    }

    public void sendTreasureChestToMoverioNotPresent(TreasureChest treasureChest) throws JSONException {
        if(this.connectedThread!=null) {
            connectedThread.write(ParserUtils.getTreasureChestJSONObjectNotPresent(treasureChest).toString().getBytes());
        }
    }

    public void sendThiefToMoverio(int amount) throws JSONException {
        if(this.connectedThread!=null) {
            connectedThread.write(ParserUtils.getThiefJSONObject(amount).toString().getBytes());
        }
    }

    public void sendResponseToMoverio(String response) throws JSONException {
        if(this.connectedThread!=null) {
            connectedThread.write(ParserUtils.getResponseJSONObject(response).toString().getBytes());
        }
    }

    public void sendAmountToMoverio(int amount) throws JSONException {
        if(this.connectedThread!=null) {
            connectedThread.write(ParserUtils.getAmountReducedJSONObject(amount).toString().getBytes());
        }
    }
}
