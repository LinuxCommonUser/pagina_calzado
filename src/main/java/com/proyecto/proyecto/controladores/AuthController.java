package com.proyecto.proyecto.controladores;

import com.proyecto.proyecto.clases.Usuario;
import com.proyecto.proyecto.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 🔥 API DNI
    private static final String API_URL = "https://dniruc.apisperu.com/api/v1/dni/";
    private static final String TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImVsZml0bzA0NkBnbWFpbC5jb20ifQ.ELGYTY8qg4VoAq5KRWCbobyEXBpcCH80mXEQ0TxpaO0";

    // Mostrar login
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        return "login";
    }

    // Mostrar registro
    @GetMapping("/register")
    public String registerView() {
        return "register";
    }

    // Registrar usuario
    @PostMapping("/register")
    public String registrar(@RequestParam String dni,
                            @RequestParam String nombre,
                            @RequestParam String telefono,
                            @RequestParam String correo,
                            @RequestParam String username,
                            @RequestParam String password,
                            Model model) {

        //  VALIDACIÓN REAL DEL DNI
        if (!validarDni(dni)) {
            model.addAttribute("error", "DNI no válido o no existe");
            return "login";
        }

        // Validar usuario existente
        if (usuarioRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "El usuario ya existe");
            return "login";
        }

        Usuario nuevo = new Usuario();
        nuevo.setDniRuc(dni);
        nuevo.setNombreCompleto(nombre);
        nuevo.setTelefono(telefono);
        nuevo.setCorreo(correo);
        nuevo.setUsername(username);
        nuevo.setPassword(passwordEncoder.encode(password));
        nuevo.setRol("ADMIN");

        usuarioRepository.save(nuevo);

        model.addAttribute("error", "Cuenta creada correctamente. Inicia sesión.");
        return "login";
    }

    // VALIDACIÓN CORRECTA DEL DNI (SIN AUTOCOMPLETAR)
    private boolean validarDni(String dni) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", TOKEN);
            headers.setAccept(MediaType.parseMediaTypes("application/json"));

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    API_URL + dni,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                return false;
            }

            Map body = response.getBody();

            // ✅ SOLO VALIDA QUE EXISTA EL DNI
            return body.containsKey("dni") && body.get("dni") != null;

        } catch (Exception e) {
            return false;
        }
    }
}