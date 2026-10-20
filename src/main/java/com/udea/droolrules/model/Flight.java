package com.udea.droolrules.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Flight {

    @NotBlank(message = "El número de vuelo es obligatorio")
    private String flightNumber;

    @NotNull(message = "Los minutos de retraso son obligatorios")
    @Min(value = 0, message = "El retraso no puede ser negativo")
    private int delayMinutes = 0;

    @NotNull(message = "La duración del vuelo es obligatoria")
    @Min(value = 0, message = "La duración del vuelo no puede ser negativa")
    private double durationHours = 1.0;

    private boolean emergencyExitAvailable = true;

    public Flight() {
    }

    public Flight(String flightNumber, int delayMinutes, double durationHours, boolean emergencyExitAvailable) {
        this.flightNumber = flightNumber;
        this.delayMinutes = delayMinutes;
        this.durationHours = durationHours;
        this.emergencyExitAvailable = emergencyExitAvailable;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getDelayMinutes() {
        return delayMinutes;
    }

    public void setDelayMinutes(int delayMinutes) {
        this.delayMinutes = delayMinutes;
    }

    public double getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(double durationHours) {
        this.durationHours = durationHours;
    }

    public boolean isEmergencyExitAvailable() {
        return emergencyExitAvailable;
    }

    public void setEmergencyExitAvailable(boolean emergencyExitAvailable) {
        this.emergencyExitAvailable = emergencyExitAvailable;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber='" + flightNumber + '\'' +
                ", delayMinutes=" + delayMinutes +
                ", durationHours=" + durationHours +
                ", emergencyExitAvailable=" + emergencyExitAvailable +
                '}';
    }
}
