package com.demo.spring.programacion.dto.habito;

public class HabitoListDto {

    private Long id;
    private String descripcion;

    public HabitoListDto(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
