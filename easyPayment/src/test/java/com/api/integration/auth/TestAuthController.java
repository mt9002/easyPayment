package com.api.integration.auth;

import com.api.auth.app.dto.RegisterDTO;
import com.api.auth.domain.entity.Client;
import com.api.integration.factory.FactoryData;
import com.api.unitest.fixture.DTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import java.util.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestAuthController {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FactoryData factoryData;

    @BeforeAll
    public static void setup() {
        DTO.beforeAll();
    }

    @Test
    @Transactional
    @Rollback
    public void testRegisterIntegration(TestInfo testInfo, TestReporter testReporter) throws Exception {

        RegisterDTO registerDTO = DTO.registerDTO;
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(registerDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Registro exitoso"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Ruperto"));
    }

    @Test
    @Transactional
    @Rollback
    public void testLoginIntegration(TestInfo testInfo, TestReporter testReporter) throws Exception {

        Client client = factoryData.createClient();

        String jsonRequest = String.format(
                """
            {
              "email": "%s",
              "password": "%s"
            }
            """, client.getEmail(), "12345");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("login exitoso"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @Transactional
    @Rollback
    public void testLoginUnauthirized(TestInfo testInfo, TestReporter testReporter) throws Exception {

        Client client = factoryData.createClient();

        String jsonRequest = String.format(
                """
            {
              "email": "%s",
              "password": "%s"
            }
            """, client.getEmail(), "123456777");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("login no exitoso"))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @Transactional
    @Rollback
    public void testLoginUnauthirizedToken(TestInfo testInfo, TestReporter testReporter) throws Exception {

        Client client = factoryData.createClient();

        String jsonRequest = String.format(
                """
            {
              "email": "%s",
              "password": "%s"
            }
            """, client.getEmail(), "123456777");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("login no exitoso"))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @Transactional
    @Rollback
    public void testLoginJWTFailed(TestInfo testInfo, TestReporter testReporter) throws Exception {

        String tokenInvalid = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30";

        mockMvc.perform(MockMvcRequestBuilders.get("/bills/findByIdBill")
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + tokenInvalid).param("id", String.valueOf(2L)))
                .andExpect(jsonPath("$.message").value("JWT validity cannot be asserted and should not be trusted."))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @Transactional
    @Rollback
    public void testLoginTokenInvalid(TestInfo testInfo, TestReporter testReporter) throws Exception {

        String tokenInvalid = "ryJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30";

        mockMvc.perform(MockMvcRequestBuilders.get("/bills/findByIdBill")
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + tokenInvalid).param("id", String.valueOf(2L)))
                .andExpect(jsonPath("$.message").value("Invalid token"))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.success").value(false));
    }

    // ############### Roles
    @Test
    @Transactional
    @Rollback
    public void testLoginRoleUserFailed(TestInfo testInfo, TestReporter testReporter) throws Exception {
        Client client = factoryData.createClient();
        String token = factoryData.loginClient(client);
        mockMvc.perform(MockMvcRequestBuilders.get("/calculadora/operaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .param("a", "2").param("a", "34"))
                .andExpect(jsonPath("$.message").value("No tienes permiso para acceder a este recurso."))
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @Transactional
    @Rollback
    public void testLoginTokenExpired(TestInfo testInfo, TestReporter testReporter) throws Exception {

        Client client = factoryData.createClient();

        String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

        String expiredToken = Jwts.builder()
                .setSubject(client.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60)) // hace 1 hora
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 30)) // expirado hace 30 seg
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)), SignatureAlgorithm.HS256)
                .compact();

        mockMvc.perform(MockMvcRequestBuilders.get("/calculadora/operaciones")
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + expiredToken)
                .param("a", "2").param("a", "34"))
                .andExpect(jsonPath("$.message").value("Token has expired"))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @Transactional
    @Rollback
    public void testfindByIdUserIntegration(TestInfo testInfo, TestReporter testReporter) throws Exception {
        Client client = factoryData.createClient();
        String token = factoryData.loginClient(client);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/findById")
                .header("Authorization", "Bearer " + token).param("id", String.valueOf(client.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User encontrado"))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Ruperto"));
    }

    @Test
    @Transactional
    @Rollback
    public void testfindByIdUserFailed(TestInfo testInfo, TestReporter testReporter) throws Exception {
        Client client = factoryData.createClient();
        String token = factoryData.loginClient(client);
        Long idFake = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/user/findById")
                .header("Authorization", "Bearer " + token).param("id", String.valueOf(idFake))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User no encontrado"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.success").value(false));
    }
}
