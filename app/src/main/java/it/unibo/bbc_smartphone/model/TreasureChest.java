package it.unibo.bbc_smartphone.model;

/**
 * Created by brando on 15/06/2015.
 */
public class TreasureChest {
    private int number;
    private double latitude;
    private double longitude;
    private int money;
    public enum State{
        UNVISITED, OPEN, LOCKED_KEY, LOCKED_COOPERATION, FINAL;
    }
    State state;


    public TreasureChest(int number, double latitude, double longitude, int money, String state) {
        this.number = number;
        this.latitude = latitude;
        this.longitude = longitude;
        this.money = money;
        this.state = State.valueOf(state);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
