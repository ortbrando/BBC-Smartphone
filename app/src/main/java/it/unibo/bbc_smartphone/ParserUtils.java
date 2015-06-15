package it.unibo.bbc_smartphone;

import org.json.JSONException;
import org.json.JSONObject;

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
}
