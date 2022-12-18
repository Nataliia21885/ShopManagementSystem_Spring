package com.application.spring.model.converter;

import com.application.spring.model.dto.ProductDto;
import com.application.spring.model.entity.Product;
import com.application.spring.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductConverter implements Converter<Product, ProductDto> {

    private final ProducerConverter producerConverter;
    private final ProducerService producerService;

    @Override
    public ProductDto toDto(Product dao) {
        ProductDto dto = new ProductDto();
        dto.setId(dao.getId());
        dto.setName(dao.getName());
        dto.setPrice(dao.getPrice());
        dto.setProducerId(producerConverter.toDto(dao.getProducer()).getId());
        return dto;
    }

    @Override
    public Product toDao(ProductDto productDto) {
        Product dao = new Product();
        dao.setId(productDto.getId());
        dao.setName(productDto.getName());
        dao.setPrice(productDto.getPrice());
        dao.setProducer(producerConverter.toDao(producerService.getById(productDto.getProducerId())));
        return dao;
    }
}
