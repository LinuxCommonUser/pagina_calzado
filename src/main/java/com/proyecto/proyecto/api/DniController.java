package com.proyecto.proyecto.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dni")
public class DniController {

    // ✅ URL CORREGIDA
    private final String API_URL = "https://api.apisperu.com/api/v1/dni/";

    private final String TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImVsZml0bzA0NkBnbWFpbC5jb20ifQ.ELGYTY8qg4VoAq5KRWCbobyEXBpcCH80mXEQ0TxpaO0";

    @GetMapping("/{dni}")
    public ResponseEntity<?> consultarDni(@PathVariable String dni) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", TOKEN);
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    API_URL + dni,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 IMPORTANTE para ver error real

            Map<String, String> error = new HashMap<>();
            error.put("error", "No se pudo consultar el DNI");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}