package com.udea.droolrules;

import com.udea.droolrules.model.CreditRequest;
import com.udea.droolrules.model.CreditResponse;
import com.udea.droolrules.model.Customer;
import com.udea.droolrules.service.CreditEvaluationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreditRulesTest {

    @Autowired
    private CreditEvaluationService creditEvaluationService;

    @Test
    @DisplayName("Prueba 1: Regla RejectLowCreditScore (score < 500)")
    void testPrueba1_RejectLowCreditScore() {
        Customer customer = new Customer("Ana Gómez", 25, 2000.0, 450);
        CreditRequest request = new CreditRequest(customer, "personal", 10000.0);

        CreditResponse response = creditEvaluationService.evaluateCredit(request);

        assertFalse(response.isApproved());
        assertEquals("Solicitud rechazada: Puntaje crediticio demasiado bajo.", response.getMessage());
    }

    @Test
    @DisplayName("Prueba 2: Regla RejectUnderAge (edad < 18)")
    void testPrueba2_RejectUnderAge() {
        Customer customer = new Customer("Luis Martínez", 16, 2000.0, 700);
        CreditRequest request = new CreditRequest(customer, "personal", 10000.0);

        CreditResponse response = creditEvaluationService.evaluateCredit(request);

        assertFalse(response.isApproved());
        assertEquals("Solicitud rechazada: El cliente debe ser mayor de 18 años.", response.getMessage());
    }

    @Test
    @DisplayName("Prueba 3: Regla PersonalCreditApproval - Tasa Baja (> 700)")
    void testPrueba3_PersonalCreditApproval_LowRate() {
        Customer customer = new Customer("Juan Pérez", 30, 2000.0, 750);
        CreditRequest request = new CreditRequest(customer, "personal", 15000.0);

        CreditResponse response = creditEvaluationService.evaluateCredit(request);

        assertTrue(response.isApproved());
        assertEquals(15000.0, response.getApprovedAmount());
        assertEquals(5.0, response.getInterestRate());
        assertEquals("Crédito personal aprobado.", response.getMessage());
    }

    @Test
    @DisplayName("Prueba 4: Regla PersonalCreditApproval - Tasa Alta (500-700) y tope 10x ingresos")
    void testPrueba4_PersonalCreditApproval_HighRate() {
        Customer customer = new Customer("María López", 28, 1500.0, 600);
        CreditRequest request = new CreditRequest(customer, "personal", 20000.0);

        CreditResponse response = creditEvaluationService.evaluateCredit(request);

        assertTrue(response.isApproved());
        assertEquals(15000.0, response.getApprovedAmount()); // 1500 * 10
        assertEquals(8.0, response.getInterestRate());
        assertEquals("Crédito personal aprobado.", response.getMessage());
    }

    @Test
    @DisplayName("Prueba 5: Regla MortgageCreditApproval - Tasa Baja (> 750)")
    void testPrueba5_MortgageCreditApproval_LowRate() {
        Customer customer = new Customer("Carlos García", 35, 4000.0, 800);
        CreditRequest request = new CreditRequest(customer, "hipotecario", 150000.0);

        CreditResponse response = creditEvaluationService.evaluateCredit(request);

        assertTrue(response.isApproved());
        assertEquals(150000.0, response.getApprovedAmount());
        assertEquals(3.5, response.getInterestRate());
        assertEquals("Crédito hipotecario aprobado.", response.getMessage());
    }

    @Test
    @DisplayName("Prueba 6: Regla MortgageCreditApproval - Tasa Alta (600-750) y tope 50x ingresos")
    void testPrueba6_MortgageCreditApproval_HighRate() {
        Customer customer = new Customer("Sofía Ramírez", 40, 3500.0, 650);
        CreditRequest request = new CreditRequest(customer, "hipotecario", 200000.0);

        CreditResponse response = creditEvaluationService.evaluateCredit(request);

        assertTrue(response.isApproved());
        assertEquals(175000.0, response.getApprovedAmount()); // 3500 * 50
        assertEquals(4.5, response.getInterestRate());
        assertEquals("Crédito hipotecario aprobado.", response.getMessage());
    }

    @Test
    @DisplayName("Prueba 7: Regla AutoCreditApproval - Tasa Baja (> 700)")
    void testPrueba7_AutoCreditApproval_LowRate() {
        Customer customer = new Customer("Pedro Sánchez", 32, 2500.0, 720);
        CreditRequest request = new CreditRequest(customer, "automotriz", 30000.0);

        CreditResponse response = creditEvaluationService.evaluateCredit(request);

        assertTrue(response.isApproved());
        assertEquals(30000.0, response.getApprovedAmount());
        assertEquals(6.0, response.getInterestRate());
        assertEquals("Crédito automotriz aprobado.", response.getMessage());
    }

    @Test
    @DisplayName("Prueba 8: Regla AutoCreditApproval - Tasa Alta (550-700) y tope 20x ingresos")
    void testPrueba8_AutoCreditApproval_HighRate() {
        Customer customer = new Customer("Laura Torres", 29, 1800.0, 600);
        CreditRequest request = new CreditRequest(customer, "automotriz", 40000.0);

        CreditResponse response = creditEvaluationService.evaluateCredit(request);

        assertTrue(response.isApproved());
        assertEquals(36000.0, response.getApprovedAmount()); // 1800 * 20
        assertEquals(7.5, response.getInterestRate());
        assertEquals("Crédito automotriz aprobado.", response.getMessage());
    }

    @Test
    @DisplayName("Prueba 9: Regla RejectInvalidCreditType - No cumple requisitos")
    void testPrueba9_RejectInvalidCreditType() {
        Customer customer = new Customer("Diego Morales", 27, 2000.0, 650);
        CreditRequest request = new CreditRequest(customer, "hipotecario", 100000.0); // Requiere ingresos >= 3000

        CreditResponse response = creditEvaluationService.evaluateCredit(request);

        assertFalse(response.isApproved());
        assertEquals("Solicitud rechazada: No cumple con los requisitos para el tipo de crédito solicitado.", response.getMessage());
    }
}
