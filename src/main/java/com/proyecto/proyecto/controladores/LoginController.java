package com.proyecto.proyecto.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto.proyecto.clases.Usuario;
import com.proyecto.proyecto.repositorios.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private static final List<Map<String, String>> usuarios = new ArrayList<>();

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario != null && usuario.getPassword().equals(password)) {
            session.setAttribute("idUsuario", usuario.getIdUsuario());
            session.setAttribute("usuario", usuario.getUsername());
            return "redirect:/";
        }
        
        model.addAttribute("error", "Credenciales incorrectas");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/register")
    public String procesarRegistro(@RequestParam String nombre, @RequestParam String correo, @RequestParam String dni,@RequestParam String telefono,
                                   @RequestParam String username, @RequestParam String password) {
                                    
        Usuario u = new Usuario();
        u.setNombre_completo(nombre);
        u.setCorreo(correo);
        u.setUsername(username);
        u.setPassword(password);
        u.setDni_ruc(dni);
        u.setTelefono(telefono);
        
        usuarioRepository.save(u);
        return "registro-exitoso";
    }
}
