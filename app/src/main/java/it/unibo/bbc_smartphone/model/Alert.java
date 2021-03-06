package it.unibo.bbc_smartphone.model;

/**
 * Created by brando on 15/06/2015.
 */
public class Alert {
    private double latitude;
    private double longitude;
    private String message;
    private int idPlayer;

    public Alert(double latitude, double longitude, String message, int idPlayer) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.message = message;
        this.idPlayer = idPlayer;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }
}
