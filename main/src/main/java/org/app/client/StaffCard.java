package org.app.client;

import org.app.server.Card;

import java.util.Date;

//All control

public class StaffCard extends Card {

    public StaffCard(int id, String name, Date expireDate, int floor, int room) {
        super(id, name, expireDate, floor, room);
    }

    @Override
    protected void acessibility() {
        setAccressibiliy("Staff");
    }
}
