package com.application.spring.service;

import com.application.spring.exception.NotFoundException;
import com.application.spring.exception.ValidationException;
import com.application.spring.model.converter.ProducerConverter;
import com.application.spring.model.dto.ProducerDto;
import com.application.spring.model.entity.Producer;
import com.application.spring.repository.ProducerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Service
public class ProducerService {

    private final ProducerRepository producerRepository;
    private final ProducerConverter producerConverter;


    public ProducerDto save(ProducerDto producerDto) throws ValidationException {
        validateProducerDto(producerDto);
        Producer savedProducer = producerRepository.save(producerConverter.toDao(producerDto));
        return producerConverter.toDto(savedProducer);
    }

    public List<ProducerDto> listAll() {
        return producerRepository.findAll()
                .stream()
                .map(producerConverter::toDto)
                .collect(Collectors.toList());
    }

    public ProducerDto getById(UUID id) {
        return producerConverter.toDto(producerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producer with id " + id + " doesn't exist")));
    }

    public void deleteById(UUID id) {
        getById(id);
        producerRepository.deleteById(id);
    }

    private void validateProducerDto(ProducerDto producerDto) throws ValidationException {
        if (producerDto.getId() == null) {
            producerDto.setId(UUID.randomUUID());
        }

        if (isNull(producerDto)) {
            throw new ValidationException("Object producer is null");
        }

        if (isNull(producerDto.getName()) || producerDto.getName().isEmpty()) {
            throw new ValidationException("Producer name is empty");
        }
    }
}
