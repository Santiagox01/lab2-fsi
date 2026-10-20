package com.udea.droolrules.model;

import java.util.ArrayList;
import java.util.List;

public class AirlineEvaluationResponse {

    private Passenger passenger;
    private Flight flight;
    private List<String> appliedRules = new ArrayList<>();
    private String summaryMessage;

    public AirlineEvaluationResponse() {
    }

    public AirlineEvaluationResponse(Passenger passenger, Flight flight, List<String> appliedRules, String summaryMessage) {
        this.passenger = passenger;
        this.flight = flight;
        this.appliedRules = appliedRules;
        this.summaryMessage = summaryMessage;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public List<String> getAppliedRules() {
        return appliedRules;
    }

    public void setAppliedRules(List<String> appliedRules) {
        this.appliedRules = appliedRules;
    }

    public String getSummaryMessage() {
        return summaryMessage;
    }

    public void setSummaryMessage(String summaryMessage) {
        this.summaryMessage = summaryMessage;
    }
}
