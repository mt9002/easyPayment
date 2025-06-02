package com.api.unitest.auth;

import com.api.auth.app.dto.RegisterDTO;
import com.api.auth.domain.entity.Client;
import com.api.auth.domain.service.AuthMapper;
import com.api.auth.domain.service.AuthService;
import com.api.auth.infra.outgoing.IAuthRepository;
import com.api.auth.infra.outgoing.UserORM;
import com.api.auth.infra.repository.AuthImplemnet;
import com.api.unitest.fixture.DTO;
import com.api.util.Response;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestAuthService {

    @Mock
    UserORM userORM;;

    @InjectMocks
    private AuthImplemnet authRepository;

    @BeforeAll
    public static void setup() {
        DTO.beforeAll();
    }

    @Test
    public void registerFailed() throws Exception {

        // Arrange
        Client client = DTO.client;
        Response expected = new Response("ERROR Register not found RuntimeException", 400, false, null);
        
        Mockito.when(userORM.save(client)).thenThrow(new RuntimeException("RuntimeException"));

        // Act
        Response resp = authRepository.register(client);

        // Assert
        asserts(expected, resp);

    }

    private void asserts(Response expected, Response actual) {
        assertEquals(expected.getMessage(), actual.getMessage());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertFalse(actual.isSuccess());
        assertEquals(expected.getData(), actual.getData());
    }
;
}
