package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapUserToUserDto() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@mail.com");
        user.setLastName("Test");
        user.setFirstName("Test");
        user.setPassword("secret");
        user.setAdmin(true);

        UserDto dto = userMapper.toDto(user);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(user.getId());
        assertThat(dto.getEmail()).isEqualTo(user.getEmail());
        assertThat(dto.getLastName()).isEqualTo(user.getLastName());
        assertThat(dto.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(dto.getPassword()).isEqualTo(user.getPassword());
        assertThat(dto.isAdmin()).isEqualTo(user.isAdmin());
    }

    @Test
    void shouldMapUserDtoToUser() {
        UserDto dto = new UserDto();
        dto.setId(2L);
        dto.setEmail("Jhonny.eddy@example.com");
        dto.setLastName("Eddy");
        dto.setFirstName("Johnny");
        dto.setPassword("pwd123");
        dto.setAdmin(false);

        User user = userMapper.toEntity(dto);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(dto.getId());
        assertThat(user.getEmail()).isEqualTo(dto.getEmail());
        assertThat(user.getLastName()).isEqualTo(dto.getLastName());
        assertThat(user.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(user.getPassword()).isEqualTo(dto.getPassword());
        assertThat(user.isAdmin()).isEqualTo(dto.isAdmin());
    }
}
