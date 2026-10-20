package com.udea.droolrules.service;

import com.udea.droolrules.model.AirlineEvaluationResponse;
import com.udea.droolrules.model.Flight;
import com.udea.droolrules.model.Passenger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirlineEvaluationService {

    @Autowired
    private KieContainer kieContainer;

    public AirlineEvaluationResponse evaluate(Passenger passenger, Flight flight) {
        KieSession kieSession = kieContainer.newKieSession();
        try {
            kieSession.insert(passenger);
            kieSession.insert(flight);
            kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }

        List<String> rules = new ArrayList<>(passenger.getAppliedRules());
        String summary = String.format("Evaluación completada para %s en el vuelo %s. Se ejecutaron %d reglas de negocio.",
                passenger.getName(), flight.getFlightNumber(), rules.size());

        return new AirlineEvaluationResponse(passenger, flight, rules, summary);
    }
}
