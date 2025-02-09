package org.app.server.floor;

public class HighFloor implements Floor {
    private int number_of_rooms;
    private int emergencyExitCount;

    public HighFloor(int number_of_rooms, int emergencyExitCount) {
        this.number_of_rooms = number_of_rooms;
        this.emergencyExitCount = emergencyExitCount;
    }

    @Override
    public int getNumberOfRooms() {
        return number_of_rooms;
    }

    @Override
    public String getFloorType() {
        return "High Floor";
    }

    @Override
    public void accessibility() {
        System.out.println("Accessibility: Elevators available");
    }

    public boolean hasPanoramicView() {
        return true;
    }

    public int getEmergencyExitCount() {
        return emergencyExitCount;
    }
}
