package it.unibo.bbc_smartphone.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brando on 15/06/2015.
 */
public class Model {
    private Match match;
    private Player player;
    private List<Alert> alerts;
    private List<Key> keys;
    
    public Model(){
        this.alerts = new ArrayList<>();
        this.keys = new ArrayList<>();
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public int getPlayerId(){
        return this.match.getId();
    }

    public void treasureReceived(TreasureChest treasureChest) {
        this.match.updateTreasureChest(treasureChest);
    }

    public void amountReduced(int amount) {
        this.match.reduceAmount(amount);
    }

    public Match getMatch(){
        return this.match;
    }

    public void setPosition(double latitude, double longitude) {
        this.match.setPosition(latitude, longitude);
    }

    public double getPlayerLatitude(){
        return this.match.getPlayerLatitude();
    }

    public double getPlayerLongitude(){
        return this.match.getPlayerLongitude();
    }

    public void addKeyToPlayer1(Key key){
        this.match.addKeyToPlayer1(key);
    }

    public void addKeyToPlayer2(Key key) {
        this.match.addKeyToPlayer2(key);
    }
}
