package com.proyecto.proyecto.controladores;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    private static final List<Map<String, String>> usuarios = new ArrayList<>();


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        // Validar contra la lista de usuarios
        for (Map<String, String> usuario : usuarios) {
            if (usuario.get("username").equals(username)
                    && usuario.get("password").equals(password)) {

                session.setAttribute("usuario", username);
                return "redirect:/";
            }
        }

        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/register")
    public String procesarRegistro(
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        // Verificar si el username ya existe
        for (Map<String, String> usuario : usuarios) {
            if (usuario.get("username").equals(username)) {
                model.addAttribute("error", "El usuario ya existe");
                return "login";
            }
        }

        // Crear usuario
        Map<String, String> nuevoUsuario = new HashMap<>();
        nuevoUsuario.put("nombre", nombre);
        nuevoUsuario.put("correo", correo);
        nuevoUsuario.put("username", username);
        nuevoUsuario.put("password", password);

        // Guardar en la lista
        usuarios.add(nuevoUsuario);

        // Enviar datos a la vista de confirmación
        model.addAttribute("nombre", nombre);
        model.addAttribute("correo", correo);
        model.addAttribute("username", username);

        return "registro-exitoso";
    }
}
