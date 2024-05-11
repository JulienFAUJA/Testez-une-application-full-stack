package com.openclassrooms.starterjwt.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.openclassrooms.starterjwt.fixtures.AuthLoginFixture;
import com.openclassrooms.starterjwt.fixtures.AuthSignupRequestTest;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.repository.UserTestRepository;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;

import static com.openclassrooms.starterjwt.fixtures.AuthLoginFixture.testLoginRequest;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserTestRepository userTestRepository;

    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @Test
    void loginWithBadPasswordLoginShouldFail() throws Exception {

        LoginRequest loginRequest = testLoginRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        loginRequest.setPassword("12340000");
        String content = objectMapper.writeValueAsString(loginRequest);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
        mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test // Indique que c'est une méthode de test
    public void testLogin_Unauthorized() throws Exception {
        // Given : Aucun utilisateur

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("bad@email.com");
        loginRequest.setPassword("ceci est un faux password");
        String jsonLoginRequest = new ObjectMapper().writeValueAsString(loginRequest);
        // When
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLoginRequest))
                // Then
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLogin_Success() throws Exception {
        // Crée une requête de connexion avec l'email et le mot de passe de l'utilisateur
        LoginRequest loginRequest = AuthLoginFixture.testLoginRequest();


        // Convertit la requête de connexion en JSON
        String jsonLoginRequest = new ObjectMapper().writeValueAsString(loginRequest);

        // When
        mockMvc.perform(post("/api/auth/login") // Effectue une requête POST à l'URL /api/auth/login
                        .contentType(MediaType.APPLICATION_JSON) // Définit le type de contenu de la requête à JSON
                        .content(jsonLoginRequest)) // Définit le contenu de la requête à la requête de connexion en JSON
                // Then
                .andExpect(status().isOk()) // Vérifie que le statut de la réponse est 200 (OK)
                .andExpect(jsonPath("$.token", is(notNullValue()))); // Vérifie que le token dans la réponse n'est pas null
    }

    @Test // Indique que c'est une méthode de test
    public void testRegister_BadRequest() throws Exception {
        // Given : Une requête d'inscription avec des informations invalides
        // Crée une requête d'inscription sans prénom, sans nom, avec un email invalide et un mot de passe trop court
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("invalid");
        signupRequest.setPassword("123");
        // Convertit la requête d'inscription en JSON
        String jsonSignupRequest = new ObjectMapper().writeValueAsString(signupRequest);
        // When
        mockMvc.perform(post("/api/auth/register") // Effectue une requête POST à l'URL /api/auth/register
                        .contentType(MediaType.APPLICATION_JSON) // Définit le type de contenu de la requête à JSON
                        .content(jsonSignupRequest)) // Définit le contenu de la requête à la requête d'inscription en JSON
                // Then
                .andExpect(status().isBadRequest()); // Vérifie que le statut de la réponse est 400 (Bad Request)

    }

    @Test
    public void testRegister_Unauthorized() throws Exception {
        // Given : Une requête register avec des informations invalides
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("bad@email.com");
        signupRequest.setPassword("ceci est un faux password");
        String jsonSignupRequest = new ObjectMapper().writeValueAsString(signupRequest);
        // When
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonSignupRequest))
                // Then
                .andExpect(status().isUnauthorized());
    }

    





}
