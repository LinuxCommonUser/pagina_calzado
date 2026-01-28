package com.proyecto.proyecto.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.proyecto.proyecto.clases.ItemCarrito;
import com.proyecto.proyecto.clases.Producto;

@Service
@SessionScope // ¡Importante! Mantiene los datos mientras el usuario navega
public class CarritoService {
    private List<ItemCarrito> items = new ArrayList<>();

    public void agregarItem(Producto producto, Integer cantidad) {
        Optional<ItemCarrito> itemExistente = items.stream()
                .filter(i -> i.getProducto().codigo.equals(producto.codigo))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + cantidad);
        } else {
            items.add(new ItemCarrito(producto, cantidad));
        }
    }

    public void eliminarItem(String codigo) {
        items.removeIf(i -> i.getProducto().codigo.equals(codigo));
    }

    public void actualizarCantidad(String codigo, Integer cantidad) {
        items.stream()
            .filter(i -> i.getProducto().codigo.equals(codigo))
            .findFirst()
            .ifPresent(item -> item.setCantidad(cantidad));
    }

    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    public double obtenerTotal() {
        return items.stream().mapToDouble(ItemCarrito::getSubtotal).sum();
    }
}
