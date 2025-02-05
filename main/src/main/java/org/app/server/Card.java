package org.app.server;



import java.util.Date;

public abstract class Card {

    private int id;
    private String name;
    private Date expire_date;
    private int floor;
    private int room;
    private String accressibiliy;
    public Card(int id, String name, Date expireDate, int floor, int room){
        this.id = id;
        this.name = name;
        this.expire_date = expireDate;
        this.floor = floor;
        this.room = room;
    }

    protected abstract void acessibility();

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Date getExpire_date() {
        return expire_date;
    }

    public int getFloor() {
        return floor;
    }

    public int getRoom() {
        return room;
    }

    public void setAccressibiliy(String accressibiliy) {
        this.accressibiliy = accressibiliy;
    }

    public String getAccressibiliy() {
        return accressibiliy;
    }

}
