package com.api.unitest;

import com.api.app.controller.auth.AuthController;
import com.api.app.dto.LoginDTO;
import com.api.app.dto.RegisterDTO;
import com.api.confg.NoSecurityConfig;
import com.api.domain.interfaces.incoming.IAuthService;
import com.api.domain.interfaces.outgoing.IJWT;
import com.api.domain.services.util.Response;
import com.api.unitest.fixture.DTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(NoSecurityConfig.class)
public class TestAuthController {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IAuthService authService;

    @MockBean
    IJWT jwtService;

    @BeforeAll
    public static void setup() {
        DTO.beforeAll();
    }

    @Test
    public void testLogin(TestInfo testInfo, TestReporter testReporter) throws Exception {
        LoginDTO loginDTO = DTO.loginDTO;

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(loginDTO);

        Response response = new Response("Login exitoso", 200, true, "Token");
        System.out.println("Ejecutando login");
        Mockito.when(authService.login(loginDTO)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login exitoso"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("Token"));
    }

    @Test
    public void testRegister(TestInfo testInfo, TestReporter testReporter) throws Exception {
        RegisterDTO registerDTO = DTO.registerDTO;

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(registerDTO);

        Response response = new Response("Registro exitoso", 200, true, "Token");
 
        Mockito.when(authService.register(registerDTO)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Registro exitoso"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value("Token"));
    }

}
