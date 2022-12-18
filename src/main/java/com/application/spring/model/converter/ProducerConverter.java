package com.application.spring.model.converter;

import com.application.spring.model.dto.ProducerDto;
import com.application.spring.model.entity.Producer;
import org.springframework.stereotype.Service;

@Service
public class ProducerConverter implements Converter<Producer, ProducerDto> {
    @Override
    public ProducerDto toDto(Producer dao) {
        ProducerDto dto = new ProducerDto();
        dto.setId(dao.getId());
        dto.setName(dao.getName());
        dto.setProducts(dao.getProducts());
        return dto;
    }

    @Override
    public Producer toDao(ProducerDto dto) {
        Producer dao = new Producer();
        dao.setId(dto.getId());
        dao.setName(dto.getName());
        dao.setProducts(dto.getProducts());
        return dao;
    }
}
