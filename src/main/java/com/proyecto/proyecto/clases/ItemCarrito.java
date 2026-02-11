package com.proyecto.proyecto.clases;

public class ItemCarrito {
    private Producto producto;
    private Integer cantidad;

    public ItemCarrito(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { 
        if (cantidad < 1) this.cantidad = 1; 
        else this.cantidad = cantidad; 
    }

    public double getSubtotal() {
        // VALIDACIÓN: Si el producto tiene precio de oferta, usamos ese.
        // Si no, usamos el precio normal.
        if (producto.getPrecioOferta() != null && producto.getPrecioOferta() > 0) {
            return producto.getPrecioOferta() * cantidad;
        } else {
            return producto.getPrecio() * cantidad;
        }
    }
}
