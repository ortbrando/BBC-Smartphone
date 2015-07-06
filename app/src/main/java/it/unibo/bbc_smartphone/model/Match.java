package it.unibo.bbc_smartphone.model;

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
            if(t.getNumber() == treasureChest.getNumber()){
                treasureChests.set(i,treasureChest);
            }
            i++;
        }
    }
}
