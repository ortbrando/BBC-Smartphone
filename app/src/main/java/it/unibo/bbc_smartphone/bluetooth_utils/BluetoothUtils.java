package it.unibo.bbc_smartphone.bluetooth_utils;

import android.util.Log;

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

    public void sendLocationToMoverio(String locationToSend){
        if(this.connectedThread!=null) {
            connectedThread.write(locationToSend.getBytes());
        }
    }




}
