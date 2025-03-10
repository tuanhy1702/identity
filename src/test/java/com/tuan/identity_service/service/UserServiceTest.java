package com.tuan.identity_service.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.tuan.identity_service.dto.request.UserCreationRequest;
import com.tuan.identity_service.dto.response.UserResponse;
import com.tuan.identity_service.entity.User;
import com.tuan.identity_service.exception.AppExeption;
import com.tuan.identity_service.repository.UserRepository;

@SpringBootTest
@TestPropertySource("/test.properties") // dùng H2 thay cho Mysql
public class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest userCreationRequest;
    private UserResponse userResponse;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2003, 2, 17);

        userCreationRequest = UserCreationRequest.builder()
                .username("tuanhy1702")
                .firstName("Tuan")
                .lastName("Khuc")
                .password("12345678")
                .dob(dob)
                .build();
        userResponse = UserResponse.builder()
                .id("c5eb792c5e5d")
                .username("tuanhy1702")
                .firstName("Tuan")
                .lastName("Khuc")
                .dob(dob)
                .build();
        user = User.builder()
                .id("c5eb792c5e5d")
                .username("tuanhy1702")
                .firstName("Tuan")
                .lastName("Khuc")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.createUser(userCreationRequest);

        // THEN
        Assertions.assertThat(response.getId()).isEqualTo("c5eb792c5e5d");
        Assertions.assertThat(response.getUsername()).isEqualTo("tuanhy1702");
    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(AppExeption.class, () -> userService.createUser(userCreationRequest));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }

    @Test
    @WithMockUser(username = "tuanhy1702") // giả lập đã xác thực token
    void getMyInfo_valid_success() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        var response = userService.getMyInfo();

        Assertions.assertThat(response.getUsername()).isEqualTo("tuanhy1702");
        Assertions.assertThat(response.getId()).isEqualTo("c5eb792c5e5d");
    }

    @Test
    @WithMockUser(username = "tuanhy1702") // giả lập đã xác thực token
    void getMyInfo_userNotFound_error() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        // WHEN
        var exception = assertThrows(AppExeption.class, () -> userService.getMyInfo());

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
    }
}
