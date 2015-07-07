package it.unibo.bbc_smartphone;

import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unibo.bbc_smartphone.model.Alert;
import it.unibo.bbc_smartphone.model.Match;
import it.unibo.bbc_smartphone.model.Thief;
import it.unibo.bbc_smartphone.model.TreasureChest;

/**
 * Created by matteo.aldini on 15/06/2015.
 */
public class ParserUtils {
    public static Pair<TreasureChest, Integer> getTreasureChestFromJSONObject(JSONObject jsonObject) throws JSONException {
        TreasureChest treasureChest = new TreasureChest(jsonObject.getInt("number"),
            jsonObject.getDouble("latitude"),
                jsonObject.getDouble("longitude"),
                    jsonObject.getInt("money"),
                        jsonObject.getString("state"));
        return new Pair<>(treasureChest, jsonObject.getInt("idPlayer"));
    }

    public static Match getMatchFromJSONObject(JSONObject jsonObject) throws JSONException {
        Match match = new Match(jsonObject.getInt("points"),
                jsonObject.getInt("maxPoints"), Calendar.getInstance(), null, jsonObject.getInt("idPlayer"));

        JSONArray treasureArray = jsonObject.getJSONArray("treasureChests");
        List<TreasureChest> treasureChestSet = new ArrayList<>();
        for(int i=0; i<treasureArray.length();i++){
            JSONObject object = treasureArray.getJSONObject(i);
            TreasureChest treasureChest;
            if(object.getString("state").equals("UNVISITED")){
                treasureChest = new TreasureChest(0, object.getDouble("latitude"), object.getDouble("longitude"), 0, object.getString("state"));
            }else {
                treasureChest = new TreasureChest(object.getInt("number"), object.getDouble("latitude"), object.getDouble("longitude"), object.getInt("points"), object.getString("state"));
            }
            treasureChestSet.add(treasureChest);
        }
        match.setTreasureChests(treasureChestSet);

        return match;
    }

    public static JSONObject getPositionToSend(double latitude, double longitude, int idPlayer) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("latitude", latitude);
        jsonObject.put("longitude", longitude);
        jsonObject.put("idPlayer", idPlayer);
        jsonObject.put("messageType", 1);
        return jsonObject;
    }



    public static JSONObject getConfirmOrRefuseCooperationMsg(String toSend, int idPlayer) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", 2);
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
        jsonObject.put("messageType", 3);
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

    public static Thief getThief(JSONObject jsonObject) throws JSONException {
        return new Thief(jsonObject.getInt("amount"), jsonObject.getInt("idPlayer"));
    }

    public static int getNewAmount(JSONObject jsonObject) throws JSONException {
        return jsonObject.getInt("amount");
    }



    //TO SEND TO GLASSES PARSERS



    public static JSONObject getTreasureChestJSONObject(TreasureChest treasureChest) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", 1);
        jsonObject.put("number", treasureChest.getNumber());
        jsonObject.put("latitude", treasureChest.getLatitude());
        jsonObject.put("longitude", treasureChest.getLongitude());
        jsonObject.put("money", treasureChest.getMoney());
        jsonObject.put("state", treasureChest.getState());
        return jsonObject;
    }

    public static JSONObject getMatchJSONObject(Match match) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", 0);
        jsonObject.put("points", 0);
        jsonObject.put("maxPoints", match.getMaxPoints());
        JSONArray treasuresArray = new JSONArray();
        for(TreasureChest t : match.getTreasureChests()){
            JSONObject treasure = new JSONObject();
            treasure.put("latitude", t.getLatitude());
            treasure.put("longitude", t.getLongitude());
            treasuresArray.put(treasure);
        }
        jsonObject.put("treasureChests", treasuresArray);
        return jsonObject;
    }

    public static JSONObject getResponseJSONObject(String toSend) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", 2);
        jsonObject.put("response", toSend);
        return jsonObject;
    }

    public static JSONObject getThiefJSONObject(int moneyToSteal) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", 3);
        jsonObject.put("amount", moneyToSteal);
        return jsonObject;
    }

    public static JSONObject getAmountReducedJSONObject(int amount) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", 4);
        jsonObject.put("amount", amount);

        return jsonObject;
    }

    public static JSONObject getPositionToSend(double latitude, double longitude) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("latitude", latitude);
        jsonObject.put("longitude", longitude);
        jsonObject.put("messageType", 5);
        return jsonObject;
    }

    public static JSONObject getTreasureChestJSONObjectNotPresent(TreasureChest treasureChest) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", 6);
        jsonObject.put("number", treasureChest.getNumber());
        jsonObject.put("latitude", treasureChest.getLatitude());
        jsonObject.put("longitude", treasureChest.getLongitude());
        jsonObject.put("money", treasureChest.getMoney());
        jsonObject.put("state", treasureChest.getState());
        return jsonObject;
    }

    public static JSONObject getAlertJSONObjectToSend(Alert alert) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", 7);
        jsonObject.put("message", alert.getMessage());
        return jsonObject;
    }

    //RECEIVE FROM GLASSES

    public static String getAlertMsgFromJSONObject(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("message");
    }

    public static JSONObject getThiefJSONObjectNotMe(int points) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", 8);
        jsonObject.put("amount", points);
        return jsonObject;
    }
}
