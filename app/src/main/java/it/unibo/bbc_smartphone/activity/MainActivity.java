package it.unibo.bbc_smartphone.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import it.unibo.bbc_smartphone.ParserUtils;
import it.unibo.bbc_smartphone.R;
import it.unibo.bbc_smartphone.bluetooth_utils.BluetoothUtils;
import it.unibo.bbc_smartphone.bluetooth_utils.ConnectThread;
import it.unibo.bbc_smartphone.model.Alert;
import it.unibo.bbc_smartphone.model.Match;
import it.unibo.bbc_smartphone.model.Model;
import it.unibo.bbc_smartphone.model.TreasureChest;
import it.unibo.bbc_smartphone.tcp_connection.TCPConnection;
import it.unibo.bbc_smartphone.gps_utils.*;


public class MainActivity extends ActionBarActivity  {
    private TCPConnection connection;
    private TextView textNumber;
    private TextView textLat;
    private TextView textLong;
    private TextView textMoney;
    private Button button;
    private Model model;
    private final static int REQUEST_ENABLE_BT = 10;
    private final static String MAC_ADDRESS_SAMSUNG = "B8:C6:8E:75:BF:0E";
    private final static String MAC_ADDRESS_MOVERIO = "88:33:14:40:74:4B";
    private final static String MAC_ADDRESS_OPO = "C0:EE:FB:35:0B:16";
    private final static String MAC_ADDRESS_MOTO = "E4:90:7E:E5:6B:28";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.model = new Model();

        this.textNumber = (TextView)findViewById(R.id.textNumber);
        this.textLat = (TextView)findViewById(R.id.textLat);
        this.textLong = (TextView)findViewById(R.id.textLong);
        this.textMoney = (TextView)findViewById(R.id.textMoney);

        this.button = (Button)findViewById(R.id.initButton);

        //GPS service is activated
        this.initGPSService();

        //TCP connection is initialized
        //this.initTCPConnection();

        //BT Connection is initialized
        this.initBluetoothConnection();
    }

    private void initGPSService(){
        GPSServiceHandler gpsServiceHandler = new GPSServiceHandler();
        GPSListener listener = new GPSListener(gpsServiceHandler);
        GPSUtils.initGPS(listener, this);
    }

    private void initTCPConnection(){
        TCPConnectionHandler tcpConnectionHandler = new TCPConnectionHandler();
        TCPConnection connection = new TCPConnection(tcpConnectionHandler);
        this.connection = connection;
        this.connection.start();
    }

    private void initBluetoothConnection(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            //if Bluetooth Service is not activated, it must be activated
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }else{
            BluetoothConnectionHandler bluetoothConnectionHandler = new BluetoothConnectionHandler();
            ConnectThread thread = new ConnectThread(mBluetoothAdapter.getRemoteDevice(MAC_ADDRESS_MOVERIO), bluetoothConnectionHandler);
            thread.start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothConnectionHandler bluetoothConnectionHandler = new BluetoothConnectionHandler();
        ConnectThread thread = new ConnectThread(mBluetoothAdapter.getRemoteDevice(MAC_ADDRESS_MOVERIO), bluetoothConnectionHandler);
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

    private void obtainReceivedMessage(Message msg) throws JSONException {
        switch (msg.what){
            case 0:
                this.matchReceived((Match) msg.obj);
                break;
            case 1:
                this.treasureReceived((Pair<TreasureChest,Integer>) msg.obj);
                break;
            case 2:
                this.confirmOrRefuseMsgReceived((Boolean) msg.obj);
                break;
            case 3:
                this.alertReceived((Alert) msg.obj);
                break;
            case 4:
                this.moneyTheftReceived((Integer) msg.obj);
                break;
            case 5:
                this.newAmountReceived((Integer) msg.obj);
                break;
        }
    }

    private void matchReceived(Match match) throws JSONException {
        this.model.setMatch(match);
        BluetoothUtils.getInstance().sendMatchToMoverio(match);
    }

    private void treasureReceived(Pair<TreasureChest, Integer> treasureChest) throws JSONException {
        this.model.treasureReceived(treasureChest.first);
        if(treasureChest.second==this.model.getPlayerId()){
            BluetoothUtils.getInstance().sendTreasureChestToMoverio(treasureChest.first);
        }else {
            BluetoothUtils.getInstance().sendTreasureChestToMoverioNotPresent(treasureChest.first);
        }
    }

    private void confirmOrRefuseMsgReceived(boolean confirm) throws JSONException {
        if(confirm){
            BluetoothUtils.getInstance().sendResponseToMoverio("OK");
        }else {
            BluetoothUtils.getInstance().sendResponseToMoverio("KO");
        }
    }

    private void alertReceived(Alert alert) throws JSONException {
        BluetoothUtils.getInstance().sendAlertToMoverio(alert);
    }

    private void moneyTheftReceived(int amount) throws JSONException {
        int points = this.model.getMatch().getPoints()-amount;
        this.model.getMatch().setPoints(points);
        BluetoothUtils.getInstance().sendThiefToMoverio(points);
    }

    private void newAmountReceived(int amount) throws JSONException {
        this.model.amountReduced(amount);
        BluetoothUtils.getInstance().sendAmountToMoverio(amount);
    }

    public class TCPConnectionHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            try {
                obtainReceivedMessage(msg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class BluetoothConnectionHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    initTCPConnection();
                    break;
                case 1:
                    if((boolean)msg.obj){
                        try {
                            connection.sendConfirmRefuseCooperationToServer("OK", model.getPlayerId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        try {
                            connection.sendConfirmRefuseCooperationToServer("KO", model.getPlayerId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    String message = (String) msg.obj;
                    Alert alert = new Alert(model.getPlayerLatitude(), model.getPlayerLongitude(), message, model.getPlayerId());
                    try {
                        connection.sendAlertToServer(alert);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public class GPSServiceHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                Log.i("HANDLER", "GPS POS CHANGED");
                Location location = (Location) msg.obj;
                try {
                    if(model.getMatch()!=null){
                        model.setPosition(location.getLatitude(), location.getLongitude());
                        connection.sendPositionToServer(location.getLatitude(), location.getLongitude(), model.getPlayerId());
                        BluetoothUtils.getInstance().sendLocationToMoverio(location.getLatitude(), location.getLongitude());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
