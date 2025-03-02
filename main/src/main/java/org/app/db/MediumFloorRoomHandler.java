package org.app.db;

import org.json.JSONArray;

public class MediumFloorRoomHandler implements RoomHandler {//

    private JSONArray bookedRooms;

    public MediumFloorRoomHandler(JSONArray bookedRooms) {
        this.bookedRooms = bookedRooms;
    }

    @Override
    public boolean isRoomBooked(String room) {
        return bookedRooms.toList().contains(room);
    }

    @Override
    public String getPrefix(String floor) {
        return "B";
    }

    @Override
    public int getRoomNumber(String floor) {
        return 200;
    }
}
