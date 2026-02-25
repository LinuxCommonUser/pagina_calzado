package com.proyecto.proyecto.api;

import com.proyecto.proyecto.clases.Usuario;
import com.proyecto.proyecto.repositorios.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioApiController {

    private final UsuarioRepository repo;

    public UsuarioApiController(UsuarioRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Usuario> listar() {
        return repo.findAll();
    }

    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        return repo.save(usuario);
    }
}