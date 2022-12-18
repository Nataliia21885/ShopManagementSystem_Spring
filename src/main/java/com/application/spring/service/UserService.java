package com.application.spring.service;


import com.application.spring.exception.NotFoundException;
import com.application.spring.exception.ValidationException;
import com.application.spring.model.converter.UserConverter;
import com.application.spring.model.dto.UsersDto;
import com.application.spring.model.entity.RoleEnum;
import com.application.spring.model.entity.Users;
import com.application.spring.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    public UsersDto save(UsersDto usersDto) throws ValidationException {
        validateUserDto(usersDto);
        Users savedUser = userRepository.save(userConverter.toDao(usersDto));
        return userConverter.toDto(savedUser);
    }

    public List<UsersDto> listAll() {
        return userRepository.findAll()
                .stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
    }

    public UsersDto getById(UUID id) {
        return userConverter.toDto(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " doesn't exist")));
    }

    public void deleteById(UUID id) {
        getById(id);
        userRepository.deleteById(id);
    }

    public void registrationUser(Users users) {

        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRoles(List.of(RoleEnum.ROLE_CLIENT));
        userRepository.save(users);
    }

    private void validateUserDto (UsersDto usersDto) throws ValidationException {
        if(usersDto.getId() == null) {
            usersDto.setId(UUID.randomUUID());
        }

        if(isNull(usersDto)) {
            throw new ValidationException("Object user is null");
        }

        if(isNull(usersDto.getEmail()) || usersDto.getEmail().isEmpty()) {
            throw new ValidationException("Email is empty");
        }

        if(isNull(usersDto.getPassword()) || usersDto.getPassword().isEmpty()) {
            throw new ValidationException("Password is empty");
        }

        if(isNull(usersDto.getFirstName()) || usersDto.getFirstName().isEmpty()) {
            throw new ValidationException("First name is empty");
        }

        if(isNull(usersDto.getLastName()) || usersDto.getLastName().isEmpty()) {
            throw new ValidationException("Last name is empty");
        }
    }
}
