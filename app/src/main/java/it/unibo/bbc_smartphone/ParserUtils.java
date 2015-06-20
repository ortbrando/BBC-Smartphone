package it.unibo.bbc_smartphone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import it.unibo.bbc_smartphone.model.Alert;
import it.unibo.bbc_smartphone.model.Match;
import it.unibo.bbc_smartphone.model.TreasureChest;

/**
 * Created by matteo.aldini on 15/06/2015.
 */
public class ParserUtils {
    public static TreasureChest getTreasureChestFromJSONObject(JSONObject jsonObject) throws JSONException {
        TreasureChest treasureChest = new TreasureChest(jsonObject.getInt("number"),
            jsonObject.getLong("latitude"),
                jsonObject.getLong("longitude"),
                    jsonObject.getInt("money"));
        return treasureChest;
    }

    public static Match getMatchFromJSONObject(JSONObject jsonObject) throws JSONException {
        Match match = new Match(jsonObject.getInt("points"),
                jsonObject.getInt("maxPoints"), Calendar.getInstance(), null, jsonObject.getInt("idPlayer"));

        JSONArray treasureArray = jsonObject.getJSONArray("treasureChests");
        Set<TreasureChest> treasureChestSet = new HashSet<>();
        for(int i=0; i<treasureArray.length();i++){
            JSONObject object = treasureArray.getJSONObject(i);
            TreasureChest treasureChest = new TreasureChest(0, object.getLong("latitude"), object.getLong("longitude"), 0);
            treasureChestSet.add(treasureChest);
        }
        match.setTreasureChests(treasureChestSet);

        return match;
    }

    public static JSONObject getPositionToSend(long latitude, long longitude, int idPlayer) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("latitude", latitude);
        jsonObject.put("longitude", longitude);
        jsonObject.put("idPlayer", idPlayer);
        jsonObject.put("messageType", "SENDPOSITION");
        return jsonObject;
    }

    public static JSONObject getConfirmOrRefuseCooperationMsg(String toSend, int idPlayer) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", "CONFIRMORREFUSECOOP");
        jsonObject.put("idPlayer", idPlayer);
        jsonObject.put("response", toSend);
        return jsonObject;
    }

    public static boolean getConfirmedOrRefused(JSONObject jsonObject) throws JSONException {
        if(jsonObject.getString("response").equals("OK")){
            return true;
        }else {
            return false;
        }
    }

    public static JSONObject getAlertToSend(Alert alert) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", "SENDALERT");
        jsonObject.put("idPlayer", alert.getIdPlayer());
        jsonObject.put("latitude", alert.getLatitude());
        jsonObject.put("longitude", alert.getLongitude());
        jsonObject.put("message", alert.getMessage());
        return jsonObject;
    }

    public static Alert getAlertFromJSONObject(JSONObject jsonObject) throws JSONException{
        Alert alert = new Alert(jsonObject.getLong("latitude"),
                jsonObject.getLong("longitude"), jsonObject.getString("message"),
                jsonObject.getInt("idPlayer"));

        return alert;
    }

    public static int getMoneyTheft(JSONObject jsonObject) throws JSONException {
        return jsonObject.getInt("amount");
    }

    public static int getNewAmount(JSONObject jsonObject) throws JSONException {
        return jsonObject.getInt("amount");
    }
}
