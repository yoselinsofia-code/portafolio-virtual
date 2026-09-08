package com.portafolio.config;

import com.portafolio.entity.Semana;
import com.portafolio.repository.SemanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Permite que Spring MVC convierta automaticamente el id de semana (String)
 * enviado desde el formulario de actividades en la entidad Semana completa.
 */
@Component
@RequiredArgsConstructor
public class StringToSemanaConverter implements Converter<String, Semana> {

    private final SemanaRepository semanaRepository;

    @Override
    public Semana convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        return semanaRepository.findById(Long.parseLong(source)).orElse(null);
    }
}
