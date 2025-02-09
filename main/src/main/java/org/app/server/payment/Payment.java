package org.app.server.payment;

import java.util.Date;

public class Payment {
    private double income;
    private String payment_id;
    private double amount;
    private boolean status;
    private Date date;
    private String customerID;

    // Constructor
    public Payment(double income, String payment_id, double amount, boolean status, Date date, String customerID) {
        this.income = income;
        this.payment_id = payment_id;
        this.amount = amount;
        this.status = status;
        this.date = date;
        this.customerID = customerID;
    }

    // Getter and Setter methods
    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    // Method implementations
    public void process_payment() {
        System.out.println("Processing payment for customer ID: " + customerID);
        this.status = true; // Simulate successful payment processing
    }

    public boolean validate_payment(String customerID) {
        System.out.println("Validating payment for customer ID: " + customerID);
        return this.customerID.equals(customerID) && this.amount > 0;
    }

    public void refuse(String customerID) {
        System.out.println("Payment refused for customer ID: " + customerID);
        this.status = false;
    }

    public String get_payment_detail(String customerID) {
        if (this.customerID.equals(customerID)) {
            return "Payment ID: " + payment_id + ", Amount: " + amount + ", Status: " + (status ? "Completed" : "Pending");
        } else {
            return "No payment details found for customer ID: " + customerID;
        }
    }

    public void update_status(String customerID) {
        if (this.customerID.equals(customerID)) {
            this.status = !this.status;
            System.out.println("Payment status updated for customer ID: " + customerID);
        } else {
            System.out.println("Failed to update status for customer ID: " + customerID);
        }
    }
}
