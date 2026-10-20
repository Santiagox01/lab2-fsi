package com.udea.droolrules.controller;

import com.udea.droolrules.model.AirlineEvaluationRequest;
import com.udea.droolrules.model.AirlineEvaluationResponse;
import com.udea.droolrules.model.Flight;
import com.udea.droolrules.model.Passenger;
import com.udea.droolrules.service.AirlineEvaluationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/airline")
public class AirlineController {

    @Autowired
    private AirlineEvaluationService evaluationService;

    // Endpoint REST API
    @PostMapping("/api/evaluate")
    @ResponseBody
    public ResponseEntity<?> evaluateAirlineApi(@Valid @RequestBody AirlineEvaluationRequest request, BindingResult result) {
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                    .orElse("Errores de validación");
            return ResponseEntity.badRequest().body(new AirlineEvaluationResponse(
                    request.getPassenger(),
                    request.getFlight(),
                    Collections.emptyList(),
                    "Error de validación: " + errorMessage
            ));
        }
        AirlineEvaluationResponse response = evaluationService.evaluate(request.getPassenger(), request.getFlight());
        return ResponseEntity.ok(response);
    }

    // Formulario Web
    @GetMapping("/form")
    public String showAirlineForm(Model model) {
        AirlineEvaluationRequest request = new AirlineEvaluationRequest();
        Passenger passenger = new Passenger();
        passenger.setName("Juan Pérez");
        passenger.setAge(30);
        passenger.setMembershipStatus("Gold");
        passenger.setLuggageWeight(8.5);
        passenger.setSeatPreference("Any");
        passenger.setLoyaltyPoints(1200);

        Flight flight = new Flight();
        flight.setFlightNumber("AV9320");
        flight.setDelayMinutes(75);
        flight.setDurationHours(2.5);
        flight.setEmergencyExitAvailable(true);

        request.setPassenger(passenger);
        request.setFlight(flight);

        model.addAttribute("airlineRequest", request);
        return "airline_form";
    }

    // Procesar evaluación desde el formulario web
    @PostMapping("/evaluate")
    public String evaluateAirlineWeb(@Valid @ModelAttribute("airlineRequest") AirlineEvaluationRequest airlineRequest,
                                     BindingResult result,
                                     Model model) {
        if (result.hasErrors()) {
            return "airline_form";
        }
        AirlineEvaluationResponse response = evaluationService.evaluate(
                airlineRequest.getPassenger(),
                airlineRequest.getFlight()
        );
        model.addAttribute("response", response);
        return "airline_result";
    }
}
