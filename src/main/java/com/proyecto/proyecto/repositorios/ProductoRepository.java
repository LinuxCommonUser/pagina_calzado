package com.proyecto.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.proyecto.clases.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Producto findByCodigo(String codigo);
    List<Producto> findByPrecioOfertaIsNotNull(); // Para ofertas
    List<Producto> findByNombreContainingOrMaterialContaining(String nombre, String material);
    

}