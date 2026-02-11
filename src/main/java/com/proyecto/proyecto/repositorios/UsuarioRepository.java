package com.proyecto.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.proyecto.clases.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}