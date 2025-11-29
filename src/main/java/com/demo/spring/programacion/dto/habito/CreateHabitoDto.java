package com.demo.spring.programacion.dto.habito;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateHabitoDto {

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El nivel de importancia es obligatorio")
    private String nivelDeImportancia;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivelDeImportancia() {
        return nivelDeImportancia;
    }

    public void setNivelDeImportancia(String nivelDeImportancia) {
        this.nivelDeImportancia = nivelDeImportancia;
    }
}