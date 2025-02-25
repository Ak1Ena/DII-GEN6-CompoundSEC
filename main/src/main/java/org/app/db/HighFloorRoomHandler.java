package org.app.db;

import org.json.JSONArray;
import org.json.JSONObject;

public class HighFloorRoomHandler implements RoomHandler {

    private JSONArray bookedRooms;

    public HighFloorRoomHandler(JSONArray bookedRooms) {
        this.bookedRooms = bookedRooms;
    }

    @Override
    public boolean isRoomBooked(String room) {
        return bookedRooms.toList().contains(room);
    }

    @Override
    public String getPrefix(String floor) {
        return "C";
    }

    @Override
    public int getRoomNumber(String floor) {
        return 300;
    }
}