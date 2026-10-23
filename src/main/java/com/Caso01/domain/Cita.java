package com.Caso01.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cita")
@Getter
@Setter
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombreMascota;
    private String nombrePropietario;
    private LocalDate fecha;
    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;
}