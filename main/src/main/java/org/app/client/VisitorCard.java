package org.app.client;

import org.app.server.Card;

import java.util.Date;

//view only

public class VisitorCard extends Card {

    public VisitorCard(int id, String name, Date expireDate, int floor, int room) {
        super(id, name, expireDate, floor, room);
    }

    @Override
    protected void acessibility() {
        setAccressibiliy("Visitor");
    }
}
