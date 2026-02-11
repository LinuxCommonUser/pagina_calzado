package com.proyecto.proyecto.controladores;

import com.proyecto.proyecto.clases.Usuario;
import com.proyecto.proyecto.clases.Venta;
import com.proyecto.proyecto.repositorios.UsuarioRepository;
import com.proyecto.proyecto.repositorios.ProductoRepository;
import com.proyecto.proyecto.repositorios.VentaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model){

        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Venta> ventas = ventaRepository.findAll();

        long totalUsuarios = usuarios.size();
        long totalAdmins = usuarios.stream().filter(u -> "ADMIN".equals(u.getRol())).count();
        long totalClientes = usuarios.stream().filter(u -> "CLIENTE".equals(u.getRol())).count();
        long totalVendedores = usuarios.stream().filter(u -> "VENDEDOR".equals(u.getRol())).count();

        long totalProductos = productoRepository.count();
        long totalVentas = ventas.size();

        // 📊 Ventas por día
        Map<LocalDate, Double> ventasPorDia = ventas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getFecha().toLocalDate(),
                        Collectors.summingDouble(v -> v.getTotal() != null ? v.getTotal() : 0)
                ));

        List<String> fechas = ventasPorDia.keySet()
                .stream()
                .sorted()
                .map(LocalDate::toString)
                .collect(Collectors.toList());

        List<Double> montos = fechas.stream()
                .map(f -> ventasPorDia.get(LocalDate.parse(f)))
                .collect(Collectors.toList());

        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("totalAdmins", totalAdmins);
        model.addAttribute("totalClientes", totalClientes);
        model.addAttribute("totalVendedores", totalVendedores);
        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("totalVentas", totalVentas);

        model.addAttribute("fechas", fechas);
        model.addAttribute("montos", montos);

        return "admin/dashboard-principal";
    }
}
