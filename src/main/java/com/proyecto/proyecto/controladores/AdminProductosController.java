package com.proyecto.proyecto.controladores;

import com.proyecto.proyecto.clases.Producto;
import com.proyecto.proyecto.repositorios.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/productos")
public class AdminProductosController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public String listar(Model model){

        List<Producto> productos = productoRepository.findAll();

        model.addAttribute("productos", productos);
        model.addAttribute("producto", new Producto());
        model.addAttribute("totalProductos", productoRepository.count());

        return "admin/dashboard-productos";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Producto producto){
        productoRepository.save(producto);
        return "redirect:/admin/productos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){

        Producto producto = productoRepository.findById(id)
                .orElseThrow();

        model.addAttribute("producto", producto);
        model.addAttribute("productos", productoRepository.findAll());

        return "admin/dashboard-productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id){
        productoRepository.deleteById(id);
        return "redirect:/admin/productos";
    }
}
