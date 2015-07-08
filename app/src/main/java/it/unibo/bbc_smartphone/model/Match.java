package it.unibo.bbc_smartphone.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by brando on 15/06/2015.
 */
public class Match {
    private int points;
    private int maxPoints;
    private Calendar dateStart;
    private Calendar dateEnd;
    private List<TreasureChest> treasureChests;
    private Player player1;
    private Player player2;

    public Match(int points, int maxPoints, Calendar dateStart, Calendar dateEnd, int idPlayer) {
        this.points = points;
        this.maxPoints = maxPoints;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        treasureChests = new ArrayList<>();
        this.player1 = new Player(idPlayer);
        this.player2 = new Player(2);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public Calendar getDateStart() {
        return dateStart;
    }

    public void setDateStart(Calendar dateStart) {
        this.dateStart = dateStart;
    }

    public Calendar getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public List<TreasureChest> getTreasureChests() {
        return treasureChests;
    }

    public void setTreasureChests(List<TreasureChest> treasureChests) {
        this.treasureChests = treasureChests;
    }

    public int getId(){
        return this.player1.getId_number();
    }

    public void updateTreasureChest(TreasureChest treasureChest){
        int i = 0;
        for(TreasureChest t : treasureChests){
            if(t.getLatitude() == treasureChest.getLatitude()
                    &&t.getLongitude() == treasureChest.getLongitude()){
                treasureChests.set(i,treasureChest);
                Log.i("STATE", TreasureChest.State.OPEN.name());
                if(treasureChest.getState().equals(TreasureChest.State.OPEN)){
                    setPoints(getPoints()+treasureChest.getMoney());
                    Log.i("POINTS", ""+this.getPoints());
                }
            }
            i++;
        }
    }

    public void reduceAmount(int amount) {
        this.maxPoints=amount;
    }

    public void setPosition(double latitude, double longitude) {
        this.player1.setLatitude(latitude);
        this.player1.setLongitude(longitude);
    }

    public double getPlayerLatitude() {
        return this.player1.getLatitude();
    }

    public double getPlayerLongitude() {
        return this.player1.getLongitude();
    }

    public void addKeyToPlayer1(Key key) {
        this.player1.addElement(key);
    }

    public void addKeyToPlayer2(Key key) {
        this.player2.addElement(key);
    }
}