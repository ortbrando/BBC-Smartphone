package it.unibo.bbc_smartphone.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*try {
            TCPConnection connection = new TCPConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }else{
            ConnectThread thread = new ConnectThread(mBluetoothAdapter.getRemoteDevice(MAC_ADDRESS_SAMSUNG));
            thread.start();
        }
        GPSListener listener = new GPSListener();
        GPSUtils.initGPS(listener, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        ConnectThread thread = new ConnectThread(mBluetoothAdapter.getRemoteDevice(MAC_ADDRESS_SAMSUNG));
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
}
