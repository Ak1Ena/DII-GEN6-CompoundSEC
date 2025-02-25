package org.app.db;

import org.json.JSONArray;

public class LowFloorRoomHandler implements RoomHandler {

    private JSONArray bookedRooms;

    public LowFloorRoomHandler(JSONArray bookedRooms) {
        this.bookedRooms = bookedRooms;
    }

    @Override
    public boolean isRoomBooked(String room) {
        return bookedRooms.toList().contains(room);
    }

    @Override
    public String getPrefix(String floor) {
        return "A";
    }

    @Override
    public int getRoomNumber(String floor) {
        return 100;
    }
}
