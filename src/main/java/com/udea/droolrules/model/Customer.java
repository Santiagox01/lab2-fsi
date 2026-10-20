package com.udea.droolrules.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Customer {
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min=2, max=100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;
    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "La edad debe ser de almenos 18 años")
    private int age;
    @NotNull(message = "Los ingresos mensuales son obligatorios")
    @Min(value = 0, message = "Los ingresos mensuales no pueden ser negativos")
    private double monthlyIncome;
    @NotNull(message = "El puntaje crediticio debe ser obligatorio")
    @Min(value = 0, message = "El puntaje crediticio debe estar entre 0 y 1000")
    private int creditScore;


    public Customer(String name, int age, double monthlyIncome, int creditScore) {
        this.name = name;
        this.age = age;
        this.monthlyIncome = monthlyIncome;
        this.creditScore = creditScore;
    }

    public Customer() {

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

        public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }


    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }
}
