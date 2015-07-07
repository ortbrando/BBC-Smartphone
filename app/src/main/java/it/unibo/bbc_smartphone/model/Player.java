package it.unibo.bbc_smartphone.model;

import android.location.Location;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by brando on 15/06/2015.
 */
public class Player {
    private int id_number;
    private Set<Key> set;
    private double latitude;
    private double longitude;

    public Player(int id_number) {
        this.id_number = id_number;
        this.set = new HashSet<>();
        this.latitude = 0;
        this.longitude = 0;
    }

    public Set<Key> getSet() {
        return set;
    }

    public void addElement(Key key) {
        this.set.add(key);
    }

    public int getId_number() {
        return id_number;
    }

    public void setId_number(int id_number) {
        this.id_number = id_number;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}