package com.openclassrooms.starterjwt.services.userService;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class UserServiceIntegrationTest {


    @Autowired
    private UserService userService;

    @Test

    void testFindById_shouldReturnUser() {
        User found = userService.findById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("yoga@studio.com");
        assertThat(found.isAdmin()).isTrue();
    }

    @Test
    void testDelete_shouldRemoveUser() {



        userService.delete(1L);


        User deleted = userService.findById(1L);
        assertThat(deleted).isNull();
    }
}
