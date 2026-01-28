package com.proyecto.proyecto.clases;

public class ItemCarrito {
    private Producto producto;
    private Integer cantidad;

    public ItemCarrito(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { 
        if (cantidad < 1) this.cantidad = 1; 
        else this.cantidad = cantidad; 
    }

    public double getSubtotal() {
        return producto.precio * cantidad;
    }
}
