package com.demo.spring.programacion.dto.usuario;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResumenDto {

    private String nombre;
    private String email;
    private String colorFavorito;
    private Long cantidadEntradas;
    private LocalDate fechaUltimaEntrada;

}