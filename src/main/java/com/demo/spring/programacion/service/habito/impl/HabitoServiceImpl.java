package com.demo.spring.programacion.service.habito.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.spring.programacion.dto.habito.CreateHabitoDto;
import com.demo.spring.programacion.dto.habito.HabitoListDto;
import com.demo.spring.programacion.mapper.habito.HabitoMapper;
import com.demo.spring.programacion.model.Habito;
import com.demo.spring.programacion.repository.habito.HabitoRepository;
import com.demo.spring.programacion.service.habito.HabitoService;

@Service
@Transactional
public class HabitoServiceImpl implements HabitoService {

    private final HabitoRepository habitoRepository;

    public HabitoServiceImpl(HabitoRepository habitoRepository) {
        this.habitoRepository = habitoRepository;
    }

    @Override
    public Habito crearHabito(CreateHabitoDto request) {
        Habito habito = HabitoMapper.toEntity(request);
        return habitoRepository.save(habito);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitoListDto> listarHabitos() {
        return HabitoMapper.toListDtoList(habitoRepository.findAll());
    }
}
