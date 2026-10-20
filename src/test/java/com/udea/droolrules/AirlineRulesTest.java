package com.udea.droolrules;

import com.udea.droolrules.model.AirlineEvaluationResponse;
import com.udea.droolrules.model.Flight;
import com.udea.droolrules.model.Passenger;
import com.udea.droolrules.service.AirlineEvaluationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AirlineRulesTest {

    @Autowired
    private AirlineEvaluationService airlineEvaluationService;

    @Test
    @DisplayName("Regla 1: Upgrade a clase ejecutiva por estatus frecuente (Gold/Platinum) y retraso > 60 min")
    void testRule1_UpgradeToBusinessClassForFrequentFlyersWithDelays() {
        Passenger passenger = new Passenger("Carlos Gomez", 35, "Gold", false, "Window", 18.0, 1000);
        Flight flight = new Flight("AV101", 75, 2.5, false);

        AirlineEvaluationResponse response = airlineEvaluationService.evaluate(passenger, flight);

        assertEquals("Business", response.getPassenger().getAssignedClass());
        assertTrue(response.getAppliedRules().stream()
                .anyMatch(r -> r.contains("UpgradeToBusinessClassForFrequentFlyersWithDelays")));
    }

    @Test
    @DisplayName("Regla 2: Check-in prioritario para adultos mayores (> 65 años)")
    void testRule2_PriorityCheckInForSeniors() {
        Passenger passenger = new Passenger("Beatriz Elena", 68, "Basic", false, "Aisle", 12.0, 100);
        Flight flight = new Flight("AV102", 10, 1.5, true);

        AirlineEvaluationResponse response = airlineEvaluationService.evaluate(passenger, flight);

        assertTrue(response.getPassenger().isPriorityCheckIn());
        assertTrue(response.getAppliedRules().stream()
                .anyMatch(r -> r.contains("PriorityCheckInForSeniors")));
    }

    @Test
    @DisplayName("Regla 3: Descuento del 10% por equipaje ligero (< 10 kg)")
    void testRule3_DiscountForLightLuggage() {
        Passenger passenger = new Passenger("Mateo Ruiz", 28, "Basic", false, "Window", 8.5, 200);
        Flight flight = new Flight("AV103", 0, 2.0, true);

        AirlineEvaluationResponse response = airlineEvaluationService.evaluate(passenger, flight);

        assertEquals(10.0, response.getPassenger().getDiscountPercentage());
        assertTrue(response.getAppliedRules().stream()
                .anyMatch(r -> r.contains("DiscountForLightLuggage")));
    }

    @Test
    @DisplayName("Regla 4: Denegar ascensos por equipaje con sobrepeso (> 23 kg)")
    void testRule4_DenyUpgradeForOverweightLuggage() {
        Passenger passenger = new Passenger("Felipe Castro", 45, "Platinum", false, "Window", 26.0, 5000);
        Flight flight = new Flight("AV104", 90, 3.0, false);

        AirlineEvaluationResponse response = airlineEvaluationService.evaluate(passenger, flight);

        assertFalse(response.getPassenger().isUpgradeEligible());
        assertNotEquals("Business", response.getPassenger().getAssignedClass());
        assertTrue(response.getAppliedRules().stream()
                .anyMatch(r -> r.contains("DenyUpgradeForOverweightLuggage")));
    }

    @Test
    @DisplayName("Regla 5: Asignar salida de emergencia a adultos jóvenes (18-40) con preferencia Any y disponibilidad")
    void testRule5_AssignEmergencyExitSeatToYoungAdults() {
        Passenger passenger = new Passenger("Daniela Ramos", 24, "Basic", false, "Any", 12.0, 300);
        Flight flight = new Flight("AV105", 15, 2.0, true);

        AirlineEvaluationResponse response = airlineEvaluationService.evaluate(passenger, flight);

        assertEquals("Emergency Exit", response.getPassenger().getAssignedSeat());
        assertFalse(response.getFlight().isEmergencyExitAvailable());
        assertTrue(response.getAppliedRules().stream()
                .anyMatch(r -> r.contains("AssignEmergencyExitSeatToYoungAdults")));
    }

    @Test
    @DisplayName("Regla 6: Compensación de $200 por retraso extremo (> 180 min)")
    void testRule6_CompensationForExtremeDelays() {
        Passenger passenger = new Passenger("Andres Morales", 32, "Basic", false, "Aisle", 14.0, 150);
        Flight flight = new Flight("AV106", 200, 3.5, false);

        AirlineEvaluationResponse response = airlineEvaluationService.evaluate(passenger, flight);

        assertEquals(200.0, response.getPassenger().getCompensationAmount());
        assertTrue(response.getAppliedRules().stream()
                .anyMatch(r -> r.contains("CompensationForExtremeDelays")));
    }

    @Test
    @DisplayName("Regla 7: 500 puntos de lealtad adicionales por vuelo de larga duración (> 5 horas) con membresía no Basic")
    void testRule7_ExtraLoyaltyPointsForLongFlights() {
        Passenger passenger = new Passenger("Sofia Vargas", 38, "Silver", false, "Window", 15.0, 1000);
        Flight flight = new Flight("AV107", 20, 6.5, false);

        AirlineEvaluationResponse response = airlineEvaluationService.evaluate(passenger, flight);

        assertEquals(1500, response.getPassenger().getLoyaltyPoints());
        assertTrue(response.getAppliedRules().stream()
                .anyMatch(r -> r.contains("ExtraLoyaltyPointsForLongFlights")));
    }

    @Test
    @DisplayName("Regla 8: Restringir equipaje > 15 kg en vuelos cortos (< 2 horas)")
    void testRule8_RestrictLuggageOnShortFlights() {
        Passenger passenger = new Passenger("Jorge Medina", 42, "Basic", false, "Window", 18.0, 250);
        Flight flight = new Flight("AV108", 0, 1.2, true);

        AirlineEvaluationResponse response = airlineEvaluationService.evaluate(passenger, flight);

        assertFalse(response.getPassenger().isLuggageAllowed());
        assertTrue(response.getAppliedRules().stream()
                .anyMatch(r -> r.contains("RestrictLuggageOnShortFlights")));
    }

    @Test
    @DisplayName("Regla 9: Acceso a salón VIP para miembros Platinum")
    void testRule9_VipLoungeAccessForPlatinumMembers() {
        Passenger passenger = new Passenger("Valeria Osorio", 39, "Platinum", false, "Aisle", 14.0, 8000);
        Flight flight = new Flight("AV109", 10, 2.5, false);

        AirlineEvaluationResponse response = airlineEvaluationService.evaluate(passenger, flight);

        assertTrue(response.getPassenger().isVipLoungeAccess());
        assertTrue(response.getAppliedRules().stream()
                .anyMatch(r -> r.contains("VipLoungeAccessForPlatinumMembers")));
    }

    @Test
    @DisplayName("Regla 10: Asiento preferencial para familias que viajan con niños y sin preferencia específica")
    void testRule10_PreferentialSeatForFamilies() {
        Passenger passenger = new Passenger("Familia Martinez", 34, "Basic", true, "Any", 14.0, 400);
        Flight flight = new Flight("AV110", 0, 2.0, true);

        AirlineEvaluationResponse response = airlineEvaluationService.evaluate(passenger, flight);

        assertEquals("Family Preferential", response.getPassenger().getAssignedSeat());
        assertTrue(response.getAppliedRules().stream()
                .anyMatch(r -> r.contains("PreferentialSeatForFamilies")));
    }

    @Test
    @DisplayName("Escenario combinado: Múltiples reglas activadas simultáneamente")
    void testCombinedScenario() {
        // Pasajero senior (>65), miembro Platinum, equipaje ligero (<10kg), vuelo con retraso extremo (>180m) de 6h
        Passenger passenger = new Passenger("Don Roberto", 70, "Platinum", false, "Window", 8.0, 5000);
        Flight flight = new Flight("AV999", 210, 6.0, false);

        AirlineEvaluationResponse response = airlineEvaluationService.evaluate(passenger, flight);

        // Debe tener:
        // R1: Ascenso a Business (Platinum + retraso > 60)
        assertEquals("Business", response.getPassenger().getAssignedClass());
        // R2: Check-in prioritario (>65)
        assertTrue(response.getPassenger().isPriorityCheckIn());
        // R3: 10% descuento por equipaje (<10kg)
        assertEquals(10.0, response.getPassenger().getDiscountPercentage());
        // R6: Compensación $200 (retraso > 180 min)
        assertEquals(200.0, response.getPassenger().getCompensationAmount());
        // R7: +500 puntos lealtad (vuelo > 5h y no Basic -> 5000 + 500 = 5500)
        assertEquals(5500, response.getPassenger().getLoyaltyPoints());
        // R9: Salón VIP (Platinum)
        assertTrue(response.getPassenger().isVipLoungeAccess());
        // Equipaje permitido porque no es vuelo corto
        assertTrue(response.getPassenger().isLuggageAllowed());

        assertEquals(6, response.getAppliedRules().size());
    }
}
