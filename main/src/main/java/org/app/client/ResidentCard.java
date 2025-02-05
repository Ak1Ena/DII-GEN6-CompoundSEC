package org.app.client;

import org.app.server.Card;

import java.util.Date;

//acrssibility

public class ResidentCard extends Card {

    public ResidentCard(int id, String name, Date expireDate, int floor, int room) {
        super(id, name, expireDate, floor, room);
    }

    @Override
    protected void acessibility() {
        setAccressibiliy("Resident");
    }
}
