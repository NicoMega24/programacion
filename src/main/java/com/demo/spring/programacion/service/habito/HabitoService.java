package com.demo.spring.programacion.service.habito;

import java.util.List;

import com.demo.spring.programacion.dto.habito.CreateHabitoDto;
import com.demo.spring.programacion.dto.habito.HabitoListDto;
import com.demo.spring.programacion.model.Habito;

public interface HabitoService {

    Habito crearHabito(CreateHabitoDto request);

    List<HabitoListDto> listarHabitos();
}
