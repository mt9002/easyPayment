package com.api.unitest;

import com.api.app.controller.UserController;
import com.api.confg.NoSecurityConfig;
import com.api.domain.entities.Client;
import com.api.domain.interfaces.incoming.IUserService;
import com.api.domain.interfaces.outgoing.IJWT;
import com.api.domain.services.util.Response;
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
    IJWT jwtService;

    @Test
    public void testgetByIdUser(TestInfo testInfo, TestReporter testReporter) throws Exception {

        Long id_user = 1L;
        Response<Client> response = new Response("usuario encontrado", 200, true, DTO.client);

        Mockito.when(iUserService.getById(id_user)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/findById")
                .param("id", String.valueOf(id_user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("usuario encontrado"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("mao"));
    }
}
