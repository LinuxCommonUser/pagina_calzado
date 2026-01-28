package com.proyecto.proyecto.clases;

public class Producto {
        public String codigo;
        public String nombre;
        public String categoria;
        public String material;
        public double precio;
        public String imagen;

        public Producto(String codigo, String nombre, String categoria, String material, double precio, String imagen) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.categoria = categoria;
            this.material = material;
            this.precio = precio;
            this.imagen = imagen;
        }
    }