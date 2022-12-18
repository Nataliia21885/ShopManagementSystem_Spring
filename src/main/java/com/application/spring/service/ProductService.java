package com.application.spring.service;


import com.application.spring.exception.NotFoundException;
import com.application.spring.exception.ValidationException;
import com.application.spring.model.converter.ProductConverter;
import com.application.spring.model.dto.ProductDto;
import com.application.spring.model.entity.Product;
import com.application.spring.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    public ProductDto save(ProductDto productDto) throws ValidationException {
        validateProductDto(productDto);
        Product savedProduct = productRepository.save(productConverter.toDao(productDto));
        return productConverter.toDto(savedProduct);
    }

    public List<ProductDto> listAll() {
        return productRepository.findAll()
                .stream()
                .map(productConverter::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getById(UUID id) {
        return productConverter.toDto(productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id " + id + " doesn't exist")));
    }

    public void deleteById(UUID id) {
        getById(id);
        productRepository.deleteById(id);
    }

    private void validateProductDto(ProductDto productDto) throws ValidationException {
        if (productDto.getId() == null) {
            productDto.setId(UUID.randomUUID());
        }

        if (isNull(productDto)) {
            throw new ValidationException("Object product is null");
        }

        if (isNull(productDto.getName()) || productDto.getName().isEmpty()) {
            throw new ValidationException("Product name is empty");
        }

        if (isNull(productDto.getPrice())) {
            throw new ValidationException("Price of product is null");
        }
    }
}
