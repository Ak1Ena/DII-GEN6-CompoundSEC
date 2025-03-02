package org.app.db;

public interface RoomHandler {//
    boolean isRoomBooked(String room);//
    String getPrefix(String floor);//
    int getRoomNumber(String floor);//
}
