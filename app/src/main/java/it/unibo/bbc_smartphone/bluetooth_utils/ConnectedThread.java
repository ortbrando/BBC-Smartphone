package it.unibo.bbc_smartphone.bluetooth_utils;
import android.bluetooth.BluetoothSocket;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
        this.bluetoothConnectionHandler.obtainMessage(0);
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
                // Send the obtained bytes to the UI activity
                /*mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                        .sendToTarget();*/
            } catch (IOException e) {
                break;
            }
        }
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