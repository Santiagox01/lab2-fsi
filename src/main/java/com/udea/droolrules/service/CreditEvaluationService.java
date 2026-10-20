package com.udea.droolrules.service;

import com.udea.droolrules.model.CreditRequest;
import com.udea.droolrules.model.CreditResponse;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditEvaluationService {
    @Autowired
    private KieContainer kieContainer;

    public CreditResponse evaluateCredit(CreditRequest request) {
        //Crear una respuesta inicial
        CreditResponse response = new CreditResponse();

        //Crear una sesion en Drools
        KieSession kieSession = kieContainer.newKieSession();
        try{
            //Insertar los hechos (request y Response en la sesion
            kieSession.insert(request);
            kieSession.insert(response);
            //Ejecutar todas las reglas
            kieSession.fireAllRules();
        }finally {
            //Libere la sesion
            kieSession.dispose();
        }
        return response;
    }
}
