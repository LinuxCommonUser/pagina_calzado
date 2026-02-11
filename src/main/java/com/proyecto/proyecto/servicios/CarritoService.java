package com.proyecto.proyecto.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.proyecto.proyecto.clases.ItemCarrito;
import com.proyecto.proyecto.clases.Producto;

@Service
@SessionScope // Mantiene los datos únicos por usuario mientras navega
public class CarritoService {
    
    private List<ItemCarrito> items = new ArrayList<>();
    
    /**
     * Agrega un item. Si ya existe, suma la cantidad.
     */
    public void agregarItem(Producto producto, Integer cantidad) {
        Optional<ItemCarrito> itemExistente = items.stream()
                .filter(i -> i.getProducto().getCodigo().equals(producto.getCodigo()))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
        } else {
            items.add(new ItemCarrito(producto, cantidad));
        }
    }

    /**
     * Elimina un producto del carrito basado en su código SKU.
     */
    public void eliminarItem(String codigo) {
        items.removeIf(i -> i.getProducto().getCodigo().equals(codigo));
    }

    /**
     * Actualiza la cantidad de un producto específico.
     */
    public void actualizarCantidad(String codigo, Integer cantidad) {
        items.stream()
            .filter(i -> i.getProducto().getCodigo().equals(codigo))
            .findFirst()
            .ifPresent(item -> item.setCantidad(cantidad));
    }

    /**
     * Devuelve la lista actual de items.
     */
    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    /**
     * Calcula el total de la compra sumando los subtotales.
     */
    public double obtenerTotal() {
        return items.stream().mapToDouble(ItemCarrito::getSubtotal).sum();
    }

    /**
     * Vacía el carrito después de una compra exitosa.
     * Es llamado por el ControladorPrincipal en finalizarCompra().
     */
    public void limpiarCarrito() {
        items.clear();
    }
}