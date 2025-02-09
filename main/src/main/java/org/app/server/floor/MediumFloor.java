package org.app.server.floor;

public class MediumFloor implements Floor {
    private int number_of_rooms;
    private boolean hasCommonArea;
    private int serviceRoomCount;

    public MediumFloor(int number_of_rooms, boolean hasCommonArea, int serviceRoomCount) {
        this.number_of_rooms = number_of_rooms;
        this.hasCommonArea = hasCommonArea;
        this.serviceRoomCount = serviceRoomCount;
    }

    @Override
    public int getNumberOfRooms() {
        return number_of_rooms;
    }

    @Override
    public String getFloorType() {
        return "Medium Floor";
    }

    @Override
    public void accessibility() {
        System.out.println("Accessibility: Stairs and limited elevators");
    }

    public boolean hasCommonArea() {
        return hasCommonArea;
    }

    public int getServiceRoomCount() {
        return serviceRoomCount;
    }
}
