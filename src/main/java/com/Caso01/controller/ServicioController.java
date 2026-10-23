package com.Caso01.controllers;

import com.Caso01.domain.Servicio;
import com.Caso01.service.ServicioService;
import com.Caso01.service.TipoServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/servicio")
public class ServicioController {

    private final ServicioService servicioService;
    private final TipoServicioService tipoServicioService;

    @GetMapping({"", "/", "/listado"})
    public String listado(Model model) {
        var servicios = servicioService.listar();
        model.addAttribute("servicios", servicios);
        model.addAttribute("tiposServicio", tipoServicioService.listar());
        model.addAttribute("servicio", new Servicio());
        return "servicio/listado";
    }
    
    @PostMapping("/guardar")
    public String guardar(
            @Valid Servicio servicio,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("servicios", servicioService.listar());
            model.addAttribute("tiposServicio", tipoServicioService.listar());
            model.addAttribute("servicio", servicio);
            return "servicio/listado";
        }

        try {
            boolean nuevo = servicio.getId() == null;
            servicioService.guardar(servicio);
            String mensaje = nuevo
                    ? "Servicio agregado correctamente."
                    : "Servicio actualizado correctamente.";
            redirectAttributes.addFlashAttribute("todoOk", mensaje);
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute(
                    "error", "No se pudo guardar el servicio. Verifica sus datos.");
        }
        return "redirect:/servicio/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(
            @RequestParam Integer id,
            RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "Servicio eliminado correctamente.";
        try {
            servicioService.eliminar(id);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "El servicio que intentas eliminar no existe.";
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "No se puede eliminar el servicio porque tiene citas asociadas.";
        } catch (Exception e) {
            titulo = "error";
            detalle = "Ocurrió un error al intentar eliminar el servicio.";
        }
        redirectAttributes.addFlashAttribute(titulo, detalle);
        return "redirect:/servicio/listado";
    }
}
