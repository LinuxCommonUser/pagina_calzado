package com.proyecto.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.proyecto.clases.Usuario;
import com.proyecto.proyecto.clases.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByUsuarioOrderByFechaDesc(Usuario usuario);
}