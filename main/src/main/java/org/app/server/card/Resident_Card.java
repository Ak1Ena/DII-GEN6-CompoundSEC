package org.app.server.card;

public class Resident_Card extends Card {
    private String apartment_number;
    private String emergency_contact;
    private boolean isOwner;

    public Resident_Card(String name, int room, int floor, int days, String password, String apartment_number) {
        super(name, room, floor, days, password);
        this.apartment_number = apartment_number;
    }

    public void setEmergencyContact(String contact) {
        this.emergency_contact = contact;
    }

    public boolean isOwner() {
        return isOwner;
    }

    @Override
    public boolean checkOwnership(String id, String name) {
        return getId().equals(id);
    }
}
