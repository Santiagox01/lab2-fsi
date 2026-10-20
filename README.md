# Laboratorio 2: Reglas del Negocio con Spring Boot y Drools

**Curso:** Fundamentos de Sistemas de Información (2026)  
**Institución:** Universidad de Antioquia  
**Estudiante:** Santiago Jaramillo ([@Santiagox01](https://github.com/Santiagox01))  
**Repositorio:** [Santiagox01/lab2-fsi](https://github.com/Santiagox01/lab2-fsi)  
**Repositorio Base del Profesor:** [diegobotia/droolsrules](https://github.com/diegobotia/droolsrules)

---

## 📋 Descripción del Proyecto

Este proyecto implementa un Sistema de Administración de Reglas de Negocio (**BRMS** - *Business Rules Management System*) utilizando el framework **Drools (KIE Group)** integrado con **Spring Boot 3** y **Java 21**.

La aplicación separa la lógica de negocio declarativa del código fuente procedural de la aplicación, permitiendo parametrizar, evaluar y modificar reglas mediante archivos `.drl` (Drools Rule Language) basados en el algoritmo Rete.

El sistema comprende dos módulos completos:
1. **Motor de Reglas de Aerolínea (Ejercicio Principal - 10 Reglas Drools):** Evaluación integral del perfil del pasajero, equipaje y estado del vuelo para asignación automática de beneficios, penalizaciones, compensaciones y ubicaciones de asiento.
2. **Evaluación de Créditos Bancarios (Módulo Tutorial - 6 Reglas Drools):** Evaluación de solicitudes crediticias (personal, hipotecario, automotriz) según score crediticio, edad e ingresos.

---

## ✈️ Las 10 Reglas de Negocio de la Aerolínea (`airline_rules.drl`)

| # | Nombre de la Regla | Condición de Activación | Acción / Efecto en Memoria de Trabajo |
|---|---|---|---|
| **1** | `UpgradeToBusinessClassForFrequentFlyersWithDelays` | Pasajero con membresía "Gold" o "Platinum", elegible para ascenso (`upgradeEligible == true`) y vuelo con retraso superior a 60 minutos. | Ascenso a clase ejecutiva (`assignedClass = "Business"`). |
| **2** | `PriorityCheckInForSeniors` | Pasajero mayor de 65 años (`age > 65`). | Activación de check-in prioritario (`priorityCheckIn = true`). |
| **3** | `DiscountForLightLuggage` | Pasajero con equipaje ligero menor a 10 kg (`luggageWeight < 10.0`). | Aplicación de un descuento del 10% en tarifa (`discountPercentage = 10.0`). |
| **4** | `DenyUpgradeForOverweightLuggage` | Pasajero con equipaje con sobrepeso superior a 23 kg (`luggageWeight > 23.0`). *(Salience 30)* | Inhabilita ascensos (`upgradeEligible = false`), revoca clase Business a Economy si aplica y notifica a la red Rete con `modify`. |
| **5** | `AssignEmergencyExitSeatToYoungAdults` | Pasajero entre 18 y 40 años, preferencia de asiento `"Any"`, no viaja con niños, y el vuelo tiene salida de emergencia disponible. | Asigna asiento `"Emergency Exit"` y marca la salida como ocupada en el vuelo (`emergencyExitAvailable = false`). |
| **6** | `CompensationForExtremeDelays` | Vuelo con retraso extremo mayor a 180 minutos (3 horas). | Otorga una compensación económica de $200 al pasajero (`compensationAmount = 200.0`). |
| **7** | `ExtraLoyaltyPointsForLongFlights` | Pasajero con estatus distinto a `"Basic"` en un vuelo con duración mayor a 5 horas. | Acredita 500 puntos de lealtad adicionales (`loyaltyPoints += 500`). |
| **8** | `RestrictLuggageOnShortFlights` | Pasajero con equipaje superior a 15 kg en un vuelo corto con duración menor a 2 horas. | Marca el equipaje como denegado / no permitido (`luggageAllowed = false`). |
| **9** | `VipLoungeAccessForPlatinumMembers` | Pasajero con categoría de membresía `"Platinum"`. | Concede acceso de cortesía al salón VIP del aeropuerto (`vipLoungeAccess = true`). |
| **10** | `PreferentialSeatForFamilies` | Pasajero viajando con niños (`travelingWithChildren == true`) y sin preferencia específica de asiento (`"Any"` o null). *(Salience 15)* | Asigna asiento preferencial para familias (`assignedSeat = "Family Preferential"`). |

---

## 🏗️ Arquitectura Técnica

- **Framework Web:** Spring Boot 3.4.10 / Spring Framework 6.2
- **Motor de Reglas:** Drools 7.74.1.Final (`drools-core`, `drools-compiler`, `kie-api`, `drools-mvel`, `drools-decisiontables`)
- **Compatibilidad Java 21:** Optimizado mediante `mvel2 2.5.2.Final` para compatibilidad nativa con Java 21 LTS (eliminación de dependencia deprecada `java.lang.Compiler`).
- **Motor de Vistas:** Thymeleaf con Bootstrap 5.3.3 responsive.
- **Validación:** Jakarta Bean Validation (`@Valid`, `@Min`, `@NotBlank`, `@NotNull`).
- **Testing:** JUnit 5 con Spring Boot Test (21 pruebas unitarias automáticas).

```
src/
├── main/
│   ├── java/com/udea/droolrules/
│   │   ├── DroolrulesApplication.java
│   │   ├── config/
│   │   │   └── DroolsConfig.java           # Construcción de KieFileSystem, KieBuilder y KieContainer
│   │   ├── controller/
│   │   │   ├── AirlineController.java      # Endpoints REST y vistas web de aerolínea
│   │   │   ├── CreditEvaluationController.java # Endpoints REST y vistas web de crédito
│   │   │   └── HomeController.java          # Portal principal
│   │   ├── model/
│   │   │   ├── AirlineEvaluationRequest.java
│   │   │   ├── AirlineEvaluationResponse.java
│   │   │   ├── Customer.java
│   │   │   ├── CreditRequest.java
│   │   │   ├── CreditResponse.java
│   │   │   ├── Flight.java
│   │   │   └── Passenger.java
│   │   └── service/
│   │       ├── AirlineEvaluationService.java # Ejecución KieSession de Aerolínea
│   │       └── CreditEvaluationService.java  # Ejecución KieSession de Crédito
│   └── resources/
│       ├── rules/
│       │   ├── airline_rules.drl             # 10 Reglas de Aerolínea
│       │   └── credit_rules.drl              # 6 Reglas de Crédito
│       ├── templates/
│       │   ├── index.html                    # Portal de navegación
│       │   ├── airline_form.html             # Formulario interactivo con presets
│       │   ├── airline_result.html           # Resultados y desglose de hechos
│       │   ├── credit_form.html              # Formulario de crédito
│       │   └── credit_result.html            # Resultados de crédito
│       └── application.properties
└── test/java/com/udea/droolrules/
    ├── AirlineRulesTest.java                 # 11 Pruebas unitarias para las 10 reglas
    ├── CreditRulesTest.java                  # 9 Pruebas unitarias para reglas de crédito
    └── DroolrulesApplicationTests.java       # Verificación de carga de contexto
```

---

## 🚀 Instrucciones de Ejecución

### Requisitos Previos
- **JDK 17 o JDK 21** instalado y configurado en el `PATH`.
- **Maven 3.8+** (o usar el ejecutable `./mvnw` incluido).

### 1. Clonar el Repositorio
```bash
git clone https://github.com/Santiagox01/lab2-fsi.git
cd lab2-fsi
```

### 2. Ejecutar Pruebas Automatizadas
Para verificar las 21 pruebas unitarias:
```bash
mvn test
```
*Salida esperada:*
```
[INFO] Tests run: 11, Failures: 0, Errors: 0, Skipped: 0 -- in com.udea.droolrules.AirlineRulesTest
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0 -- in com.udea.droolrules.CreditRulesTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0 -- in com.udea.droolrules.DroolrulesApplicationTests
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### 3. Iniciar el Servidor Web
```bash
mvn spring-boot:run
```
La aplicación iniciará en `http://localhost:8080`.

---

## 🌐 Interfaz Web y Demostración

Una vez iniciada la aplicación, acceda desde su navegador:

- **Portal Principal:** `http://localhost:8080/`
- **Simulador de Aerolínea:** `http://localhost:8080/airline/form`
  - Incluye **10 botones de prueba rápida ("Presets")** para validar cada regla en 1 clic.
- **Simulador de Crédito:** `http://localhost:8080/credit/form`

---

## 📡 API REST (JSON)

### 1. Evaluar Reglas de Aerolínea
- **Método:** `POST`
- **URL:** `http://localhost:8080/airline/api/evaluate`
- **Headers:** `Content-Type: application/json`

#### Ejemplo de Petición (cURL):
```bash
curl -X POST http://localhost:8080/airline/api/evaluate \
  -H "Content-Type: application/json" \
  -d '{
    "passenger": {
      "name": "Carlos Gomez",
      "age": 35,
      "membershipStatus": "Gold",
      "travelingWithChildren": false,
      "seatPreference": "Window",
      "luggageWeight": 8.0,
      "loyaltyPoints": 1500
    },
    "flight": {
      "flightNumber": "AV101",
      "delayMinutes": 75,
      "durationHours": 6.0,
      "emergencyExitAvailable": true
    }
  }'
```

#### Respuesta:
```json
{
  "passenger": {
    "name": "Carlos Gomez",
    "age": 35,
    "membershipStatus": "Gold",
    "travelingWithChildren": false,
    "seatPreference": "Window",
    "luggageWeight": 8.0,
    "loyaltyPoints": 2000,
    "priorityCheckIn": false,
    "upgradeEligible": true,
    "vipLoungeAccess": false,
    "assignedSeat": "Standard Seat",
    "assignedClass": "Business",
    "discountPercentage": 10.0,
    "compensationAmount": 0.0,
    "luggageAllowed": true,
    "appliedRules": [
      "DiscountForLightLuggage: 10% de descuento por equipaje ligero (< 10kg)",
      "UpgradeToBusinessClassForFrequentFlyersWithDelays: Ascenso a clase ejecutiva por estatus frecuente y retraso > 60m",
      "ExtraLoyaltyPointsForLongFlights: 500 puntos de lealtad adicionales por vuelo de larga duración (> 5h)"
    ]
  },
  "flight": {
    "flightNumber": "AV101",
    "delayMinutes": 75,
    "durationHours": 6.0,
    "emergencyExitAvailable": true
  },
  "appliedRules": [
    "DiscountForLightLuggage: 10% de descuento por equipaje ligero (< 10kg)",
    "UpgradeToBusinessClassForFrequentFlyersWithDelays: Ascenso a clase ejecutiva por estatus frecuente y retraso > 60m",
    "ExtraLoyaltyPointsForLongFlights: 500 puntos de lealtad adicionales por vuelo de larga duración (> 5h)"
  ],
  "summaryMessage": "Evaluación completada para Carlos Gomez en el vuelo AV101. Se ejecutaron 3 reglas de negocio."
}
```

---

## 📄 Informe del Laboratorio

El informe completo de sustentación se encuentra en los siguientes formatos con tipografía y diseño monocromático académico (estricto color negro):
- Markdown: `INFORME_LABORATORIO_2.md`
- HTML: `INFORME_LABORATORIO_2.html`
- PDF: `INFORME_LABORATORIO_2.pdf`