package org.app.server.card;

public class Staff_Card extends Card{
    private String position;
    private String department;
    private String access_level;

    public Staff_Card(String name, int room, int floor, int days, String password, String position) {
        super(name, room, floor, days, password);
        this.position = position;
    }

    public void setAccessLevel(String access_level) {
        this.access_level = access_level;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public boolean checkOwnership(String id, String name) {
        return getId().startsWith(name);
    }
}
