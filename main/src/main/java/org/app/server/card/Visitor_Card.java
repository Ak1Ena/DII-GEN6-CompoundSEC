package org.app.server.card;

import java.util.Date;

import org.app.server.enceypt.Encryption;

public class Visitor_Card extends Card{

    private String purpose;
    private String contact_person;
    private Date visit_date;

    Visitor_Card(String name, int room, int floor, int days,String password) {
        super(name, room, floor, days,password);
    }

    @Override
    public void setPiority(String piority) {
        super.setPiority("Visitor");
    }

    public void setVisit_date(Date visit_date) {
        this.visit_date = visit_date;
    }

    public String getPurpose() {
        return purpose;
    }

    public Date getVisit_date() {
        return visit_date;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public void setVisit_Date(Date date){
        this.visit_date = date;
    }

    @Override
    public boolean checkOwnership(String id,String psw) {
        if (Encryption.decrypt(id)[5].equals(psw)){return true;}
        else if (!Encryption.decrypt(id)[5].equals(psw)) {return false;}
        else {System.out.println("ERROR! Please try again");return false;}
    }


}
