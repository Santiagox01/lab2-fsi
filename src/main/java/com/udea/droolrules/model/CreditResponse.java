package com.udea.droolrules.model;

public class CreditResponse {

    private boolean approved;
    private double approvedAmount;
    private double interestRate;
    private String message;


    public CreditResponse() {

    }

    public CreditResponse(boolean approved, double approvedAmount, double interestRate, String message) {
        this.approved = approved;
        this.approvedAmount = approvedAmount;
        this.interestRate = interestRate;
        this.message = message;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public double getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(double approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
