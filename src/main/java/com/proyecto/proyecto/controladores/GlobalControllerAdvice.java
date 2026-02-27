package com.proyecto.proyecto.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.proyecto.proyecto.servicios.CarritoService;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CarritoService carritoService;

    @ModelAttribute
    public void agregarDatosGlobales(Authentication auth, HttpSession session) {

        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {

            session.setAttribute("usuario", auth.getName());

            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            session.setAttribute("rol", isAdmin ? "ADMIN" : "CLIENTE");

            int cantidad = carritoService.obtenerItems().stream()
                    .mapToInt(i -> i.getCantidad()).sum();

            session.setAttribute("carritoCantidad", cantidad);

        } else {
            session.setAttribute("usuario", null);
            session.setAttribute("rol", null);
            session.setAttribute("carritoCantidad", 0);
        }
    }
}