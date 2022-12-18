package com.application.spring.model.converter;

public interface Converter<T, E> {

    E toDto(T dao);

    T toDao(E dto);
}
