
package com.Caso01.controllers;

import com.Caso01.domain.Servicio;
import com.Caso01.service.ServicioService;
import com.Caso01.service.TipoServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final MessageSource messageSource;

    @GetMapping("/listado")
    public String listado(Model model) {
        var servicios = servicioService.listar();
        model.addAttribute("servicios", servicios);
        model.addAttribute("totalServicios", servicios.size());
        return "servicio/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("servicio", new Servicio());
        model.addAttribute("tiposServicio", tipoServicioService.listar());
        return "servicio/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes) {
        var resultado = servicioService.buscar(id);
        if (resultado.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "error", "No se encontró el servicio solicitado.");
            return "redirect:/servicio/listado";
        }
        model.addAttribute("servicio", resultado.get());
        model.addAttribute("tiposServicio", tipoServicioService.listar());
        return "servicio/formulario";
    }
    
    @PostMapping("/guardar")
    public String guardar(
            @Valid Servicio servicio,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tiposServicio", tipoServicioService.listar());
            return "servicio/formulario";
        }
        try {
            servicioService.guardar(servicio);
            String mensaje = servicio.getId() == null
                    ? "Servicio agregado correctamente."
                    : "Servicio actualizado correctamente.";
            redirectAttributes.addFlashAttribute("todoOk", mensaje);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("tiposServicio", tipoServicioService.listar());
            model.addAttribute(
                    "error", "No se pudo guardar el servicio. Verifica sus datos.");
            return "servicio/formulario";
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
