package org.app.server.card;

import java.util.Date;

import org.app.server.enceypt.Encryption;

public abstract class Card {
    private String id,name;
    private int room,floor,days;
    private Date expite_date;
    private String piority;
    private String password;

    Card(String name,int room,int floor,int days,String password){
        this.id = setId(name, room, floor, days);
        this.name = name;
        this.room = room;
        this.floor = floor;
        this.days = days;
        this.password = password;
    }

    public void setExpite_date(Date expite_date) {
        this.expite_date = expite_date;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPiority(String piority) {
        this.piority = piority;
    }


    //ID -> Encryption -> return
    public String setId(String name,int room,int floor,int days) {
        return Encryption.encrypt(name, room, floor, days);
    }

    public String getId() {
        return id;
    }

    //on process
    public abstract boolean checkOwnership(String id, String name);
}
