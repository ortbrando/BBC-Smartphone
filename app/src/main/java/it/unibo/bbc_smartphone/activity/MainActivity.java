package it.unibo.bbc_smartphone.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

import it.unibo.bbc_smartphone.R;
import it.unibo.bbc_smartphone.bluetooth_utils.ConnectThread;
import it.unibo.bbc_smartphone.tcp_connection.TCPConnection;
import it.unibo.bbc_smartphone.gps_utils.*;


public class MainActivity extends ActionBarActivity  {
    private final static int REQUEST_ENABLE_BT = 10;
    private final static String MAC_ADDRESS_SAMSUNG = "B8:C6:8E:75:BF:0E";
    private final static String MAC_ADDRESS = "88:33:14:22:25:3C";
    private TCPConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GPS service is activated
        this.initGPSService();

        //TCP connection is initialized
        this.initTCPConnection();

        //BT Connection is initialized
        this.initBluetoothConnection();

    }

    private void initGPSService(){
        GPSServiceHandler gpsServiceHandler = new GPSServiceHandler();
        GPSListener listener = new GPSListener(gpsServiceHandler);
        GPSUtils.initGPS(listener, this);
    }

    private void initTCPConnection(){
        try {
            TCPConnectionHandler tcpConnectionHandler = new TCPConnectionHandler();
            TCPConnection connection = new TCPConnection(tcpConnectionHandler);
            this.connection = connection;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initBluetoothConnection(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            //if Bluetooth Service is not activated, it must be activated
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }else{
            BluetoothConnectionHandler bluetoothConnectionHandler = new BluetoothConnectionHandler();
            ConnectThread thread = new ConnectThread(mBluetoothAdapter.getRemoteDevice(MAC_ADDRESS_SAMSUNG), bluetoothConnectionHandler);
            thread.start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothConnectionHandler bluetoothConnectionHandler = new BluetoothConnectionHandler();
        ConnectThread thread = new ConnectThread(mBluetoothAdapter.getRemoteDevice(MAC_ADDRESS_SAMSUNG), bluetoothConnectionHandler);
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class TCPConnectionHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    public class BluetoothConnectionHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    public class GPSServiceHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }
}
