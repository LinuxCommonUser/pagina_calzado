package com.proyecto.proyecto.controladores;

import com.proyecto.proyecto.clases.Usuario;
import com.proyecto.proyecto.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuariosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 🔹 LISTAR USUARIOS
    @GetMapping
    public String verUsuarios(Model model){

        List<Usuario> usuarios = usuarioRepository.findAll();

        long totalUsuarios = usuarioRepository.count();
        long totalAdmins = usuarios.stream().filter(u -> "ADMIN".equals(u.getRol())).count();
        long totalClientes = usuarios.stream().filter(u -> "CLIENTE".equals(u.getRol())).count();
        long totalVendedores = usuarios.stream().filter(u -> "VENDEDOR".equals(u.getRol())).count();

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("totalAdmins", totalAdmins);
        model.addAttribute("totalClientes", totalClientes);
        model.addAttribute("totalVendedores", totalVendedores);
        model.addAttribute("usuario", new Usuario());

        return "admin/dashboard-usuarios";
    }

    // GUARDAR USUARIO (CREAR Y EDITAR)
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario){
        usuarioRepository.save(usuario);
        return "redirect:/admin/usuarios";
    }

    // EDITAR USUARIO
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model){

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        model.addAttribute("usuario", usuario);
        model.addAttribute("usuarios", usuarioRepository.findAll());

        return "admin/dashboard-usuarios";
    }

    // ELIMINAR USUARIO
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id){
        usuarioRepository.deleteById(id);
        return "redirect:/admin/usuarios";
    }
}
