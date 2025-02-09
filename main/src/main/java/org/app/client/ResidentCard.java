package org.app.client;

import org.app.server.Card;

import java.util.Calendar;
import java.util.Date;

//acrssibility

public class ResidentCard extends Card {

    private int id;
    private String name;
    private  Date expireDate;
    private int floor;
    private int room;


    public ResidentCard(int id, String name, Date expireDate, int floor, int room) {
        super(id, name, expireDate, floor, room);
    }
    //expire setting
        //ask for days;
    public Date expireDATE(int days){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,days);
        return calendar.getTime();
    }

    //time stamp


    @Override
    protected void acessibility() {
        setAccressibiliy("Resident");
    }
}
