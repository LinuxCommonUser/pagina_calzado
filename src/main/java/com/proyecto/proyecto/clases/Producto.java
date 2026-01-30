package com.proyecto.proyecto.clases;

public class Producto {
        public String codigo;
        public String nombre;
        public String categoria;
        public String material;
        public double precio;
        public String imagen;
        public String descripcion;

        public Producto(String codigo, String nombre, String categoria, String material, double precio, String imagen) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.categoria = categoria;
            this.material = material;
            this.precio = precio;
            this.imagen = imagen;
        }

        public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
            return descripcion;
        }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}