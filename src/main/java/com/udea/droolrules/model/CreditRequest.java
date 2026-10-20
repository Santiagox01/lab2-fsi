package com.udea.droolrules.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreditRequest {
@NotNull(message = "El cliente es obligatorio")
@Valid
private Customer customer;
@NotBlank(message = "El tipo de credito es obligatorio")
private String creditType;  //personal, hipotecatio y vehiculo
    @NotNull(message = "El monto solicitado es obligatorio")
    @Min(value = 1000, message = "El monto solicitado debe ser de al menos $1000 ")
    private double requestAmount;

    public CreditRequest() {
    }

    public CreditRequest(Customer customer, String creditType, double requestAmount) {
        this.customer = customer;
        this.creditType = creditType;
        this.requestAmount = requestAmount;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }


    public double getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(double requestAmount) {
        this.requestAmount = requestAmount;
    }
}
