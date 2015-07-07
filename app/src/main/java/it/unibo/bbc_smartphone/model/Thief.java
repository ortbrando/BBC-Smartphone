package it.unibo.bbc_smartphone.model;

/**
 * Created by matteo.aldini on 07/07/2015.
 */
public class Thief {
    private int amount;
    private int idPlayer;

    public Thief(int amount, int idPlayer) {
        this.amount = amount;
        this.idPlayer = idPlayer;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }
}
