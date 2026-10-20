package com.udea.droolrules.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Passenger {

    @NotBlank(message = "El nombre del pasajero es obligatorio")
    private String name;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    private int age;

    @NotBlank(message = "El estatus de membresía es obligatorio (Basic, Silver, Gold, Platinum)")
    private String membershipStatus = "Basic";

    private boolean travelingWithChildren = false;

    private String seatPreference = "Any"; // Any, Window, Aisle

    @Min(value = 0, message = "El peso del equipaje no puede ser negativo")
    private double luggageWeight = 0.0;

    private int loyaltyPoints = 0;

    // Campos calculados por reglas Drools
    private boolean priorityCheckIn = false;
    private boolean upgradeEligible = true;
    private boolean vipLoungeAccess = false;
    private String assignedSeat = "Standard Seat";
    private String assignedClass = "Economy";
    private double discountPercentage = 0.0;
    private double compensationAmount = 0.0;
    private boolean luggageAllowed = true;
    private List<String> appliedRules = new ArrayList<>();

    public Passenger() {
    }

    public Passenger(String name, int age, String membershipStatus, boolean travelingWithChildren,
                     String seatPreference, double luggageWeight, int loyaltyPoints) {
        this.name = name;
        this.age = age;
        this.membershipStatus = membershipStatus;
        this.travelingWithChildren = travelingWithChildren;
        this.seatPreference = seatPreference;
        this.luggageWeight = luggageWeight;
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(String membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public boolean isTravelingWithChildren() {
        return travelingWithChildren;
    }

    public void setTravelingWithChildren(boolean travelingWithChildren) {
        this.travelingWithChildren = travelingWithChildren;
    }

    public String getSeatPreference() {
        return seatPreference;
    }

    public void setSeatPreference(String seatPreference) {
        this.seatPreference = seatPreference;
    }

    public double getLuggageWeight() {
        return luggageWeight;
    }

    public void setLuggageWeight(double luggageWeight) {
        this.luggageWeight = luggageWeight;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public boolean isPriorityCheckIn() {
        return priorityCheckIn;
    }

    public void setPriorityCheckIn(boolean priorityCheckIn) {
        this.priorityCheckIn = priorityCheckIn;
    }

    public boolean isUpgradeEligible() {
        return upgradeEligible;
    }

    public void setUpgradeEligible(boolean upgradeEligible) {
        this.upgradeEligible = upgradeEligible;
    }

    public boolean isVipLoungeAccess() {
        return vipLoungeAccess;
    }

    public void setVipLoungeAccess(boolean vipLoungeAccess) {
        this.vipLoungeAccess = vipLoungeAccess;
    }

    public String getAssignedSeat() {
        return assignedSeat;
    }

    public void setAssignedSeat(String assignedSeat) {
        this.assignedSeat = assignedSeat;
    }

    public String getAssignedClass() {
        return assignedClass;
    }

    public void setAssignedClass(String assignedClass) {
        this.assignedClass = assignedClass;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getCompensationAmount() {
        return compensationAmount;
    }

    public void setCompensationAmount(double compensationAmount) {
        this.compensationAmount = compensationAmount;
    }

    public boolean isLuggageAllowed() {
        return luggageAllowed;
    }

    public void setLuggageAllowed(boolean luggageAllowed) {
        this.luggageAllowed = luggageAllowed;
    }

    public List<String> getAppliedRules() {
        if (appliedRules == null) {
            appliedRules = new ArrayList<>();
        }
        return appliedRules;
    }

    public void setAppliedRules(List<String> appliedRules) {
        this.appliedRules = appliedRules;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", membershipStatus='" + membershipStatus + '\'' +
                ", travelingWithChildren=" + travelingWithChildren +
                ", seatPreference='" + seatPreference + '\'' +
                ", luggageWeight=" + luggageWeight +
                ", loyaltyPoints=" + loyaltyPoints +
                ", priorityCheckIn=" + priorityCheckIn +
                ", upgradeEligible=" + upgradeEligible +
                ", vipLoungeAccess=" + vipLoungeAccess +
                ", assignedSeat='" + assignedSeat + '\'' +
                ", assignedClass='" + assignedClass + '\'' +
                ", discountPercentage=" + discountPercentage +
                ", compensationAmount=" + compensationAmount +
                ", luggageAllowed=" + luggageAllowed +
                ", appliedRules=" + appliedRules +
                '}';
    }
}
