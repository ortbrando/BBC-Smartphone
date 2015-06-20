package it.unibo.bbc_smartphone.model;

/**
 * Created by brando on 15/06/2015.
 */
public class Alert {
    private long latitude;
    private long longitude;
    private String message;
    private int idPlayer;

    public Alert(long latitude, long longitude, String message, int idPlayer) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.message = message;
        this.idPlayer = idPlayer;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
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
