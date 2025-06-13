package com.api.unitest.auth;

import com.api.auth.presentation.controller.AuthController;
import com.api.auth.presentation.dto.LoginDTO;
import com.api.auth.presentation.dto.RegisterDTO;
import com.api.auth.domain.ports.in.IAuthService;
import com.api.auth.app.service.util.Response;
import com.api.auth.app.service.util.ResultState;
import com.api.unitest.fixture.DTO;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class TestAuthController {

    @InjectMocks
    AuthController authController;

    @Mock 
    IAuthService authService;

    @BeforeAll
    public static void setup() {
        DTO.beforeAll();
    }


    @Test
    public void testLogin(TestInfo testInfo, TestReporter testReporter) throws Exception {
        
        // Arrange
        LoginDTO loginDTO = DTO.loginDTO;

        Response response = Response.success("Login exitoso", "Token");
        Mockito.when(authService.login(loginDTO)).thenReturn(response);
        
        // Act
        ResponseEntity<Response> resp = authController.login(loginDTO);
        
        // Assert
        assertNotNull(response);
        assertEquals("Login exitoso", resp.getBody().getMessage());
        assertEquals("Token", resp.getBody().getData());
        
    }

    @Test
    public void testRegister(TestInfo testInfo, TestReporter testReporter) throws Exception {
        
        // Arrange
        RegisterDTO registerDTO = DTO.registerDTO;
        Response response = Response.success("Registro exitoso", "TokenFake");
        Mockito.when(authService.register(registerDTO)).thenReturn(response);

        // Act
        ResponseEntity<Response> resp = authController.register(registerDTO);
        
        // Assert
        assertNotNull(response);
        assertEquals("Registro exitoso", resp.getBody().getMessage());
        assertEquals("TokenFake", resp.getBody().getData());
    }

}
