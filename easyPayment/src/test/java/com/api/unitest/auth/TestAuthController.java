package com.api.unitest.auth;

import com.api.auth.app.controller.AuthController;
import com.api.auth.app.dto.LoginDTO;
import com.api.auth.app.dto.RegisterDTO;
import com.api.auth.domain.incoming.IAuthService;
import com.api.util.Response;
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

        Response response = new Response("Login exitoso", 200, true, "Token");
        Mockito.when(authService.login(loginDTO)).thenReturn(response);
        
        // Act
        authController.login(loginDTO);
        
        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertTrue(response.isSuccess());
        
    }

    @Test
    public void testRegister(TestInfo testInfo, TestReporter testReporter) throws Exception {
        
        // Arrange
        RegisterDTO registerDTO = DTO.registerDTO;
        Response response = new Response("Registro exitoso", 200, true, "TokenFake");
        Mockito.when(authService.register(registerDTO)).thenReturn(response);

        // Act
        Response resp = authController.register(registerDTO);
        
        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertTrue(response.isSuccess());
        assertEquals("TokenFake", resp.getData())
        
        
       ;
    }

}
