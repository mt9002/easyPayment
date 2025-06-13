package com.api.unitest;

import com.api.auth.presentation.controller.UserController;
import com.api.confg.NoSecurityConfig;
import com.api.auth.infra.presistence.entityJpa.Client;
import com.api.auth.domain.ports.in.IUserService;
import com.api.auth.infra.security.jwt.JWT;
import com.api.auth.app.service.util.Response;
import com.api.auth.app.service.util.ResultState;
import com.api.unitest.fixture.DTO;
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

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(NoSecurityConfig.class)
public class TestUserController {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private IUserService iUserService;

    @BeforeAll
    public static void setup() {
        DTO.beforeAll();
    }

    @MockBean
    JWT jwtService;

    @Test
    public void testgetByIdUser(TestInfo testInfo, TestReporter testReporter) throws Exception {

        Long id_user = 1L;
        Response<Client> response = new Response("usuario encontrado", ResultState.SUCCESS, DTO.userJpa);

        Mockito.when(iUserService.getById(id_user)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/findById")
                .param("id", String.valueOf(id_user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("usuario encontrado"))
               
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("mao"));
    }
}
