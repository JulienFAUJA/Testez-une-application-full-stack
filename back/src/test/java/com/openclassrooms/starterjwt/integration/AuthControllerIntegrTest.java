package com.openclassrooms.starterjwt.integration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.fixtures.AuthSignupRequestTest;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static com.openclassrooms.starterjwt.fixtures.AuthLoginFixture.testLoginRequest;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrTest {
    @Autowired
    private MockMvc mockMvc;

    private AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthController controller;

    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);


    @Test
    void testAuthenticateUser() throws Exception {
        LoginRequest loginRequest = testLoginRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("loginn:"+objectMapper.writeValueAsString(loginRequest));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }


    @Test
    public void testRegisterUser_SuccessfulRegistration() throws Exception {

        SignupRequest register_user = AuthSignupRequestTest.testSignUpRequestFakeEmail();
        // Créer une instance de la classe Random
        Random random = new Random();
        // Générer un nombre aléatoire entre 1000 et 9999 pour le suffixe de l'email
        int rnd = random.nextInt(9000) + 1000;
        // Concaténer le suffixe aléatoire avec l'adresse email
        String email_suffix = "@test.com";
        register_user.setEmail("toto" + rnd + email_suffix);

        when(userRepository.existsByEmail(register_user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(register_user.getPassword())).thenReturn("encodedPassword");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        //System.out.println("register_res:"+objectMapper.writeValueAsString(register_user));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register_user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User registered successfully!"))
                .andReturn();
    }






}
