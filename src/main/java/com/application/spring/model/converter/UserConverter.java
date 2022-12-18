package com.application.spring.model.converter;

import com.application.spring.model.dto.UsersDto;
import com.application.spring.model.entity.Users;
import org.springframework.stereotype.Service;

@Service
public class UserConverter implements Converter<Users, UsersDto> {
    @Override
    public UsersDto toDto(Users dao) {
        UsersDto dto = new UsersDto();
        dto.setId(dao.getId());
        dto.setEmail(dao.getEmail());
        dto.setPassword(dao.getPassword());
        dto.setFirstName(dao.getFirstName());
        dto.setLastName(dao.getLastName());
        dto.setRoles(dao.getRoles());
        return dto;
    }

    @Override
    public Users toDao(UsersDto dto) {
        Users dao = new Users();
        dao.setId(dto.getId());
        dao.setEmail(dto.getEmail());
        dao.setPassword(dto.getPassword());
        dao.setFirstName(dto.getFirstName());
        dao.setLastName(dto.getLastName());
        dao.setRoles(dto.getRoles());
        return dao;
    }
}
