package com.proyecto.proyecto.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyecto.proyecto.clases.Producto;
import com.proyecto.proyecto.servicios.CarritoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ControladorPrincipal {

    @Autowired
    private CarritoService carritoService;

    private List<Producto> listaProductos = new ArrayList<>();
    private List<Producto> ofertas = new ArrayList<>();

    public ControladorPrincipal() {
        listaProductos.add(new Producto("CB-001", "Mocasín Elegante", "Caballero", "Cuero Legítimo", 180.00, "https://oechsle.vteximg.com.br/arquivos/ids/21256873-800-800/imageUrl_1.jpg?v=638844427504570000"));
        listaProductos.add(new Producto("CB-002", "Stiletto Nude", "Dama", "Gamuza", 145.00, "https://calimodpruebaio.vtexassets.com/arquivos/ids/254826/7YT00700025_1-zapato-de-vestir-estileto-con-taco-aguja-color-nude.jpg?v=638512388037000000"));
        listaProductos.add(new Producto("CB-003", "Bota de Cuero Premium", "Dama", "Cuero", 210.00, "https://oechsle.vteximg.com.br/arquivos/ids/16866428/image-0.jpg?v=638363847528000000"));
        listaProductos.add(new Producto("CB-004", "Zapatilla Urbana", "Unisex", "Lona", 95.00, "https://plazavea.vteximg.com.br/arquivos/ids/31181508-418-418/image-019b5f07537e4612adb19789254a45d3.jpg"));
        listaProductos.add(new Producto("CB-005", "MM Slingback", "Dama", "Cuero", 229.00, "https://media.falabella.com.pe/falabellaPE/142770016_01/width=480,height=480,quality=70,format=webp,fit=pad"));
        listaProductos.add(new Producto("CB-006", "Stiletto Verano", "Dama", "Generico", 150.00, "https://media.falabella.com.pe/falabellaPE/150362485_01/width=480,height=480,quality=70,format=webp,fit=pad"));
        listaProductos.add(new Producto("CB-007", "Bota casual", "Dama", "Cuero", 149.00, "https://media.falabella.com.pe/falabellaPE/145023465_01/width=480,height=480,quality=70,format=webp,fit=pad"));
        listaProductos.add(new Producto("CB-008", "Crystal Blanco", "Unisex", "Cuero", 95.00, "https://media.falabella.com.pe/falabellaPE/146613606_01/width=480,height=480,quality=70,format=webp,fit=pad"));
        listaProductos.add(new Producto("CB-009", "Calzado Sirena", "Dama", "Cuero", 199.00, "https://media.falabella.com.pe/falabellaPE/150744779_01/width=480,height=480,quality=70,format=webp,fit=pad"));
        listaProductos.add(new Producto("CB-010", "Botin", "Caballero", "Cuero", 299.00, "https://media.falabella.com.pe/falabellaPE/133234058_01/width=480,height=480,quality=70,format=webp,fit=pad"));
        listaProductos.add(new Producto("CB-011", "Zapato Scuderiia", "Caballero", "Cuero", 210.00, "https://media.falabella.com.pe/falabellaPE/20178235_1/width=480,height=480,quality=70,format=webp,fit=pad"));
        listaProductos.add(new Producto("CB-012", "Zapatilla Urbana", "Caballero", "Lona", 249.00, "https://media.falabella.com.pe/falabellaPE/21100558_01/width=480,height=480,quality=70,format=webp,fit=pad"));
        ofertas.add(new Producto("CB-013", "Zapatilla Basica", "Caballero", "Cuero", 100.00, "https://vialepe.vtexassets.com/arquivos/ids/190962/ZAPATILLA-0-CASUAL-HOMBRE-CUERO-VIALE-HOMME-FLAT-4-RICARDO-025-MARRON-1_1000px-1.jpg?v=638544040861800000"));
        ofertas.add(new Producto("CB-014", "Stiletto Viale", "Dama", "Cuero", 150.00, "https://vialepe.vtexassets.com/arquivos/ids/191824/ZAPATO-STILETTO-VESTIR-DAMA-CUERO-VIALE-TACO-7-HER-2411-VISON-1.jpg?v=638544042037000000"));
        ofertas.add(new Producto("CB-015", "Bota Pauli", "Dama", "Sintético", 80.00, "https://d3fvqmu2193zmz.cloudfront.net/items_2/uid_commerces.1/uid_items_2.FDC9GFJT1TAO/500x500/65C62D6324F92-Bota-Dama-B-Pauli.webp"));
        ofertas.add(new Producto("CB-016", "Zapato Estelar", "Caballero", "Cuero", 145.00, "https://greenbay.pe/cdn/shop/files/ESTELAR_-_3024G-NEGRO_1.webp?v=1746816605"));
        ofertas.add(new Producto("CB-017", "Zapato casual", "Caballero", "Cuero", 199.00, "https://media.falabella.com.pe/falabellaPE/883301166_1/width=480,height=480,quality=70,format=webp,fit=pad"));
        ofertas.add(new Producto("CB-018", "Zapato casual", "Dama", "Cuero", 150.00, "https://media.falabella.com.pe/falabellaPE/139621729_01/width=480,height=480,quality=70,format=webp,fit=pad"));
        ofertas.add(new Producto("CB-019", "Botin Science Mid", "Caballero", "Sintético", 299.00, "https://media.falabella.com.pe/falabellaPE/18903384_1/width=480,height=480,quality=70,format=webp,fit=pad"));
        ofertas.add(new Producto("CB-020", "Zapatilla Urbana VI Court", "Dama", "Lona", 199.00, "https://media.falabella.com.pe/falabellaPE/20118360_1/width=480,height=480,quality=70,format=webp,fit=pad"));
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("productosDestacados",ofertas);
        return "index";
    }

    @GetMapping("/catalogo")
    public String catalogo(Model model, 
                        @RequestParam(required = false) String buscar, 
                        @RequestParam(required = false) String categoria) {
        
    List<Producto> filtrados = listaProductos.stream()
        .filter(p -> (buscar == null || buscar.isBlank()) || 
                    p.getNombre().toLowerCase().contains(buscar.toLowerCase()) || 
                    p.getMaterial().toLowerCase().contains(buscar.toLowerCase()))
        .filter(p -> (categoria == null || categoria.equals("todos") || categoria.isBlank()) || 
                    p.getCategoria().equalsIgnoreCase(categoria))
        .collect(Collectors.toList());

        model.addAttribute("productos", filtrados);
        model.addAttribute("busquedaActual", buscar);
        model.addAttribute("categoriaActual", categoria);

        return "catalogo";
    }

    @GetMapping("/producto/{cod}")
    public String verDetalles(@PathVariable("cod") String cod, Model model, @RequestParam(required = false) String origen) {
        Producto encontrado = listaProductos.stream()
                .filter(p -> p.getCodigo().equals(cod))
                .findFirst()
                .orElse(null);

        if (encontrado == null){
            encontrado = ofertas.stream()
                .filter(p -> p.getCodigo().equals(cod))
                .findFirst()
                .orElse(null);
        }

        String rutaVolver = "/catalogo";
        String nombreOrigen = "Catálogo";

        if ("ofertas".equals(origen)) {
            rutaVolver = "/ofertas";
            nombreOrigen = "Ofertas";
        }

        model.addAttribute("p", encontrado);
        model.addAttribute("rutaVolver", rutaVolver);
        model.addAttribute("nombreOrigen", nombreOrigen);
        
        return "detalle_producto"; 
    }

    @GetMapping("/ofertas")
    public String ofertas(Model model,
    @RequestParam(required = false) String buscar, 
                        @RequestParam(required = false) String categoria) {
        List<Producto> filtrados = ofertas.stream()
        .filter(p -> (buscar == null || buscar.isBlank()) || 
                    p.getNombre().toLowerCase().contains(buscar.toLowerCase()) || 
                    p.getMaterial().toLowerCase().contains(buscar.toLowerCase()))
        .filter(p -> (categoria == null || categoria.equals("todos") || categoria.isBlank()) || 
                    p.getCategoria().equalsIgnoreCase(categoria))
        .collect(Collectors.toList());

        model.addAttribute("productos", filtrados);
        model.addAttribute("busquedaActual", buscar);
        model.addAttribute("categoriaActual", categoria);
        return "ofertas";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

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
    public String agregarAlCarrito(@PathVariable String codigo, @RequestHeader(required = false) String referer, HttpSession session) {

        // Verificar si está logeado
        if (session.getAttribute("usuario") == null) {
            return "NO_LOGIN";
        }
        // Verificación en Lista de Productos
        Producto prod = listaProductos.stream()
                .filter(p -> p.codigo.equals(codigo))
                .findFirst()
                .orElse(null);

        // Verificación en Lista de Ofertas
        if (prod == null) {
            prod = ofertas.stream()
                    .filter(p -> p.codigo.equals(codigo))
                    .findFirst()
                    .orElse(null);
        }
        // Si lo encontró lo agrega
        if (prod != null) {
            carritoService.agregarItem(prod, 1);
        }

        // No lo encuentra da error
        if(prod == null){
            return "Error";
        }
        
        return "Ok";
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
}