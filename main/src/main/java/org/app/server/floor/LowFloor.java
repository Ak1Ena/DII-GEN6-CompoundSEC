package org.app.server.floor;

public class LowFloor implements Floor {
    private int number_of_rooms;
    private boolean directAccessToGround;
    private boolean isPetFriendly;

    public LowFloor(int number_of_rooms, boolean directAccessToGround, boolean isPetFriendly) {
        this.number_of_rooms = number_of_rooms;
        this.directAccessToGround = directAccessToGround;
        this.isPetFriendly = isPetFriendly;
    }

    @Override
    public int getNumberOfRooms() {
        return number_of_rooms;
    }

    @Override
    public String getFloorType() {
        return "Low Floor";
    }

    @Override
    public void accessibility() {
        System.out.println("Accessibility: Direct ground access");
    }

    public boolean hasDirectAccessToGround() {
        return directAccessToGround;
    }

    public boolean isPetFriendly() {
        return isPetFriendly;
    }
}
