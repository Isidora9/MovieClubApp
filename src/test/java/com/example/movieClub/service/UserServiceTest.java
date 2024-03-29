package com.example.movieClub.service;

import com.example.movieClub.model.User;
import com.example.movieClub.model.dto.UserDto;
import com.example.movieClub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.movieClub.MovieTestData.userBuilder;
import static com.example.movieClub.model.dto.UserDtoMapper.entitiesToDtos;
import static com.example.movieClub.model.dto.UserDtoMapper.entityToDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldCreateUser() {
        User user = User.builder().name("Sam").build();
        when(userRepository.save(user)).thenReturn(user);
        assertThat(userService.createUser(entityToDto(user))).isEqualTo(entityToDto(user));
    }

    @Test
    public void shouldGetUsers() {
        User user1 = userBuilder("Ana", "ana@gmail.com");
        User user2 = userBuilder("Mila", "mila@gmail.com");
        List<User> users = List.of(user1, user2);
        when(userRepository.findAll()).thenReturn(users);
        assertThat(userService.getUsers()).isEqualTo(entitiesToDtos(users));
    }

    @Test
    public void shouldUpdateUser() {
        User user = userBuilder("Ana", "ana@gmail.com");
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        UserDto userDto = UserDto.builder().name("Sara").email("sara@gmail.com").build();
        when(userRepository.save(user)).thenReturn(user);
        assertThat(userService.updateUser(userDto, 1L)).isEqualTo(entityToDto(user));
    }

    @Test
    public void shouldGetUserById() {
        User user = userBuilder("Ana", "ana@gmail.com");
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        assertThat(userService.getUserById(1L)).isEqualTo(entityToDto(user));
    }

    @Test
    public void shouldDeleteUserById() {
        userService.deleteUserById(1L);
        verify(userRepository, Mockito.times(1)).deleteById(1L);
    }
}