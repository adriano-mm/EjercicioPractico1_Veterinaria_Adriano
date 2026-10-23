package com.Caso01.controller;

import com.Caso01.domain.TipoServicio;
import com.Caso01.service.TipoServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tipo-servicio")
public class TipoServicioController {
    private final TipoServicioService tipoServicioService;

    @GetMapping
    public String listar(Model model) {
        var tipos = tipoServicioService.listar();
        model.addAttribute("tipos", tipos);
        model.addAttribute("totalTipos", tipos.size());
        return "tipo-servicio/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("tipoServicio", new TipoServicio());
        return "tipo-servicio/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes) {
        var resultado = tipoServicioService.buscar(id);
        if (resultado.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "error", "Tipo de servicio no encontrado.");
            return "redirect:/tipo-servicio";
        }
        model.addAttribute("tipoServicio", resultado.get());
        return "tipo-servicio/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("tipoServicio") TipoServicio tipoServicio,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "tipo-servicio/formulario";
        }
        boolean nuevo = tipoServicio.getId() == null;
        try {
            tipoServicioService.guardar(tipoServicio);
            String mensaje = nuevo
                    ? "Tipo de servicio agregado correctamente."
                    : "Tipo de servicio actualizado correctamente.";
            redirectAttributes.addFlashAttribute("todoOk", mensaje);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute(
                    "error", "No se pudo guardar el tipo de servicio.");
            return "tipo-servicio/formulario";
        }
        return "redirect:/tipo-servicio";
    }

    @PostMapping("/eliminar")
    public String eliminar(
            @org.springframework.web.bind.annotation.RequestParam Integer id,
            RedirectAttributes redirectAttributes) {
        String atributo = "todoOk";
        String mensaje = "Tipo de servicio eliminado correctamente.";
        try {
            tipoServicioService.eliminar(id);
        } catch (IllegalArgumentException e) {
            atributo = "error";
            mensaje = "El tipo de servicio que intentas eliminar no existe.";
        } catch (IllegalStateException e) {
            atributo = "error";
            mensaje = "No se puede eliminar porque tiene servicios asociados.";
        } catch (Exception e) {
            atributo = "error";
            mensaje = "Ocurrió un error al eliminar el tipo de servicio.";
        }
        redirectAttributes.addFlashAttribute(atributo, mensaje);
        return "redirect:/tipo-servicio";
    }
}
