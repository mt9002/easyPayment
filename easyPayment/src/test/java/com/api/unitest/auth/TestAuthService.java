package com.api.unitest.auth;

import com.api.auth.domain.entity.User;
import com.api.unitest.fixture.DTO;
import com.api.auth.app.service.util.Response;
import com.api.auth.app.service.util.ResultState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.api.auth.infra.presistence.ClientORM;
import com.api.auth.infra.presistence.repository.UserImplement;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.InjectMocks;

@ExtendWith(MockitoExtension.class)
public class TestAuthService {

    @Mock
    ClientORM userORM;;

    @InjectMocks
    private UserImplement userRepository;

    @BeforeAll
    public static void setup() {
        DTO.beforeAll();
    }

    @Test
    public void registerFailed() throws Exception {

        // Arrange
        User user = DTO.user;
        
        User userexpected = new User("Mao", "Tov", "mao@gmail", "12345");
        
        Mockito
        .when(userORM.save(Mockito.any())).thenThrow(new RuntimeException("RuntimeException"));

        // Act
        User userActual = userRepository.register(user);

        // Assert
        assertEquals(userexpected.getEmail(), userActual.getEmail());

    }
}
