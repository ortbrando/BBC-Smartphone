package it.unibo.bbc_smartphone.bluetooth_utils;
import android.bluetooth.BluetoothSocket;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import it.unibo.bbc_smartphone.ParserUtils;
import it.unibo.bbc_smartphone.activity.MainActivity;

/**
 * Created by brando on 28/05/2015.
 */
public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private MainActivity.BluetoothConnectionHandler bluetoothConnectionHandler;

    public ConnectedThread(BluetoothSocket socket, MainActivity.BluetoothConnectionHandler bluetoothConnectionHandler) {
        mmSocket = socket;
        this.bluetoothConnectionHandler = bluetoothConnectionHandler;
        this.bluetoothConnectionHandler.obtainMessage(0).sendToTarget();
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                String str = new String(buffer, 0, bytes, "UTF-8");
                this.receiveMessage(str);
            } catch (IOException e) {
                break;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void receiveMessage(String s) throws JSONException {
        JSONObject jsonObject = new JSONObject(s);
        int messageType = -1;
        Object obj = null;
        switch (jsonObject.getInt("messageType")){
            case 1:
                messageType = 1;
                obj = ParserUtils.getConfirmedOrRefused(jsonObject);
                break;
            case 2:
                messageType = 2;
                obj = ParserUtils.getAlertMsgFromJSONObject(jsonObject);
                break;
        }
        this.bluetoothConnectionHandler.obtainMessage(messageType, obj).sendToTarget();
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}