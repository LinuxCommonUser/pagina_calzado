package com.proyecto.proyecto.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.proyecto.proyecto.servicios.CarritoService;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CarritoService carritoService;

    @ModelAttribute
    public void agregarDatosGlobales(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            int cantidad = carritoService.obtenerItems().stream()
                            .mapToInt(i -> i.getCantidad()).sum();
            session.setAttribute("carritoCantidad", cantidad);
        } else {
            session.setAttribute("carritoCantidad", 0);
        }
    }
}
