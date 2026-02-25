package com.proyecto.proyecto.controladores;

import com.proyecto.proyecto.clases.Usuario;
import com.proyecto.proyecto.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Mostrar login
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model) {

        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }

        return "login";
    }

    // Mostrar registro (opcional si tienes vista aparte)
    @GetMapping("/register")
    public String registerView() {
        return "register"; // si no tienes esta vista, puedes eliminar este método
    }

    //  Registrar usuario
   @PostMapping("/register")
public String registrar(@RequestParam String dni,
                        @RequestParam String nombre,
                        @RequestParam String telefono,
                        @RequestParam String correo,
                        @RequestParam String username,
                        @RequestParam String password,
                        Model model) {
    // Busca en la BD si ya existe ese username
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

    // 🔐 ENCRIPTAR
    nuevo.setPassword(passwordEncoder.encode(password));

    // 🔥 IMPORTANTE
    nuevo.setRol("ADMIN");

    usuarioRepository.save(nuevo);

    model.addAttribute("error", "Cuenta creada correctamente. Inicia sesión.");
    return "login";
}
}