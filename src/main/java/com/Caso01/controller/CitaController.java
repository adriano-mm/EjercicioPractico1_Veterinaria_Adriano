package com.Caso01.controller;

import com.Caso01.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cita")
public class CitaController {
    private final CitaService citaService;

    @GetMapping("/listado")
    public String listar(Model model) {
        model.addAttribute("citas", citaService.listar());
        return "cita/listado";
    }
}