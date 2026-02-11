package com.proyecto.proyecto.controladores;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.proyecto.clases.DetalleVenta;
import com.proyecto.proyecto.clases.ItemCarrito;
import com.proyecto.proyecto.clases.Producto;
import com.proyecto.proyecto.clases.Usuario;
import com.proyecto.proyecto.clases.Venta;
import com.proyecto.proyecto.repositorios.ProductoRepository;
import com.proyecto.proyecto.repositorios.UsuarioRepository;
import com.proyecto.proyecto.repositorios.VentaRepository;
import com.proyecto.proyecto.servicios.CarritoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ControladorPrincipal {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // Obtener Comprador

    @Autowired
    private VentaRepository ventaRepository; // Guardar Compra

    @Autowired
    private CarritoService carritoService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("productosDestacados", productoRepository.findByPrecioOfertaIsNotNull());
        return "index";
    }

    @GetMapping("/catalogo")
    public String catalogo(Model model, 
                           @RequestParam(required = false) String buscar,
                           @RequestParam(required = false) String categoria) {
        
        List<Producto> productos;

        if (buscar != null && !buscar.isBlank()) {
            productos = productoRepository.findByNombreContainingOrMaterialContaining(buscar, buscar);
        } else {
            productos = productoRepository.findAll();
        }

        if (categoria != null && !categoria.equals("todos") && !categoria.isBlank()) {
            productos = productos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
        }

        model.addAttribute("productos", productos);
        model.addAttribute("busquedaActual", buscar);
        model.addAttribute("categoriaActual", categoria);
        return "catalogo";
    }

    @GetMapping("/producto/{cod}")
    public String verDetalles(@PathVariable("cod") String cod, Model model) {
        Producto prod = productoRepository.findByCodigo(cod);
        
        model.addAttribute("p", prod);
        model.addAttribute("rutaVolver", "/catalogo");
        return "detalle_producto";
    }

    @GetMapping("/ofertas")
    public String ofertas(Model model) {
        List<Producto> ofertas = productoRepository.findByPrecioOfertaIsNotNull();
        model.addAttribute("productos", ofertas);
        return "ofertas";
    }

    @GetMapping("/nosotros")
    public String nosotros() { return "nosotros"; }

    @GetMapping("/contacto")
    public String contacto() { return "contacto"; }

    @GetMapping("/carrito")
    public String verCarrito(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        model.addAttribute("items", carritoService.obtenerItems());
        model.addAttribute("total", carritoService.obtenerTotal());
        return "carrito";
    }

    @GetMapping("/carrito/agregar/{codigo}")
    @ResponseBody
    public String agregarAlCarrito(@PathVariable String codigo, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "NO_LOGIN";
        }

        Producto prod = productoRepository.findByCodigo(codigo);

        if (prod != null) {
            carritoService.agregarItem(prod, 1);
            return "Ok";
        }

        return "Error";
    }

    @GetMapping("/carrito/eliminar/{codigo}")
    public String eliminarDelCarrito(@PathVariable String codigo) {
        carritoService.eliminarItem(codigo);
        return "redirect:/carrito";
    }

    @PostMapping("/carrito/actualizar")
    public String actualizarCantidad(@RequestParam String codigo, @RequestParam Integer cantidad) {
        carritoService.actualizarCantidad(codigo, cantidad);
        return "redirect:/carrito";
    }

    @GetMapping("/compra-exitosa")
    public String compraExitosa() {
        return "compra-exitosa";
    }

    @PostMapping("/carrito/finalizar")
    public String finalizarCompra(HttpSession session) {
        String username = (String) session.getAttribute("usuario");
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) return "redirect:/login";

        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setTotal(carritoService.obtenerTotal());
        venta.setFecha(LocalDateTime.now());

        List<DetalleVenta> detalles = new ArrayList<>();
        for (ItemCarrito item : carritoService.obtenerItems()) {
            DetalleVenta det = new DetalleVenta();
            det.setVenta(venta);
            det.setProducto(item.getProducto());
            det.setCantidad(item.getCantidad());
            
            det.setPrecio_unitario_momento(item.getProducto().getPrecio());

            det.setSubtotal(item.getSubtotal());
            
            detalles.add(det);
        }
        
        venta.setDetalles(detalles);
        ventaRepository.save(venta);

        carritoService.limpiarCarrito();

        return "redirect:/compra-exitosa";
    }
}