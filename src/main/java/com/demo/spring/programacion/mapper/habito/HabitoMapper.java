package com.demo.spring.programacion.mapper.habito;

import java.util.List;
import java.util.stream.Collectors;

import com.demo.spring.programacion.dto.habito.CreateHabitoDto;
import com.demo.spring.programacion.dto.habito.HabitoListDto;
import com.demo.spring.programacion.model.Habito;
import com.demo.spring.programacion.model.NivelDeImportanciaEnum;

public final class HabitoMapper {

    private HabitoMapper() {}

    public static Habito toEntity(CreateHabitoDto req) {
        if (req == null) return null;

        Habito h = new Habito();
        h.setDescripcion(req.getDescripcion());
        h.setNivelDeImportanciaEnum(
            NivelDeImportanciaEnum.valueOf(req.getNivelDeImportancia().toUpperCase())
        );
        return h;
    }

    public static HabitoListDto toListDto(Habito entity) {
        if (entity == null) return null;

        return new HabitoListDto(
            entity.getId(),
            entity.getDescripcion()
        );
    }

    public static List<HabitoListDto> toListDtoList(List<Habito> entities) {
        return entities.stream()
                .map(HabitoMapper::toListDto)
                .collect(Collectors.toList());
    }
}
