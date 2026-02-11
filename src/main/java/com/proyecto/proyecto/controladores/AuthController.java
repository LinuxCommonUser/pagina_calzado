package com.proyecto.proyecto.controladores;

import com.proyecto.proyecto.clases.Usuario;
import com.proyecto.proyecto.repositorios.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Mostrar login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Procesar login
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (usuario.getPassword().equals(password)) {

                session.setAttribute("usuario", usuario.getUsername());
                session.setAttribute("rol", usuario.getRol());
                session.setAttribute("usuarioObj", usuario);

                if (usuario.getRol().equals("ADMIN")) {
                    return "redirect:/admin/dashboard";
                }

                return "redirect:/";
            }
        }

        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "login";
    }

    // Registro
    @PostMapping("/register")
    public String registrar(@RequestParam String dni,
                            @RequestParam String nombre,
                            @RequestParam String telefono,
                            @RequestParam String correo,
                            @RequestParam String username,
                            @RequestParam String password,
                            Model model) {

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
        nuevo.setPassword(password);
        nuevo.setRol("CLIENTE");

        usuarioRepository.save(nuevo);

        model.addAttribute("error", "Cuenta creada correctamente. Inicia sesión.");
        return "login";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
