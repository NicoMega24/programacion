package com.demo.spring.programacion.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.programacion.dto.habito.CreateHabitoDto;
import com.demo.spring.programacion.dto.habito.HabitoListDto;
import com.demo.spring.programacion.model.Habito;
import com.demo.spring.programacion.service.habito.HabitoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/habitos")
@Validated
public class HabitoController {

    private final HabitoService habitoService;

    public HabitoController(HabitoService habitoService) {
        this.habitoService = habitoService;
    }

    @PostMapping
    public ResponseEntity<Habito> crearHabito(@Valid @RequestBody CreateHabitoDto request) {

        Habito creado = habitoService.crearHabito(request);

        URI location = URI.create("api/v1/habitos/" + creado.getId());
        return ResponseEntity.created(location).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<HabitoListDto>> listarHabitos() {
        return ResponseEntity.ok(habitoService.listarHabitos());
    }
}
