package com.proyecto.proyecto.controladores;

import com.proyecto.proyecto.clases.Venta;
import com.proyecto.proyecto.repositorios.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/ventas")
public class AdminVentasController {

    @Autowired
    private VentaRepository ventaRepository;

    // LISTAR VENTAS
    @GetMapping
    public String listarVentas(Model model){

        List<Venta> ventas = ventaRepository.findAll();

        Double totalGeneral = ventas.stream()
                .mapToDouble(v -> v.getTotal() != null ? v.getTotal() : 0)
                .sum();

        model.addAttribute("ventas", ventas);
        model.addAttribute("totalVentas", ventas.size());
        model.addAttribute("totalGeneral", totalGeneral);

        return "admin/dashboard-ventas";
    }

    // VER DETALLE DE UNA VENTA
    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable Long id, Model model){

        Venta venta = ventaRepository.findById(id)
                .orElseThrow();

        model.addAttribute("venta", venta);

        return "admin/detalle-venta";
    }
}
