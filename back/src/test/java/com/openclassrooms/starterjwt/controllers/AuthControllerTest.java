package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.openclassrooms.starterjwt.fixtures.AuthLoginFixture.testLoginRequest;
import static com.openclassrooms.starterjwt.fixtures.AuthSignupRequestTest.testSignUpRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthController controller;

    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);


    @Test
    void testAuthenticateUser() {

        LoginRequest loginRequest = testLoginRequest();
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "yoga@test.com", "Julien", "Faujanet", false, "mot de passe");
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("azerty");

        ResponseEntity<?> response = controller.authenticateUser(loginRequest);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Nested
    class Register {
        @Test
        void testRegisterUser_EmailExists() {
            // Arrange
            SignupRequest signUpRequest = testSignUpRequest();
            when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);

            // Act
            ResponseEntity<?> response = controller.registerUser(signUpRequest);

            // Assert
            assertEquals(400, response.getStatusCodeValue()); // Assuming 400 is the status code for bad request
            assertEquals("Error: Email is already taken!", ((MessageResponse) response.getBody()).getMessage());
            verify(userRepository, times(0)).save(any()); // Verify that userRepository.save was not called
        }

        @Test
        void testRegisterUser_EmailDoesNotExist() {
            // Arrange
            SignupRequest signUpRequest = testSignUpRequest();
            when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
            when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn("encodedPassword");

            // Act
            ResponseEntity<?> response = controller.registerUser(signUpRequest);

            // Assert
            assertEquals(200, response.getStatusCodeValue()); // Assuming 200 is the status code for success
            assertEquals("User registered successfully!", ((MessageResponse) response.getBody()).getMessage());
            verify(userRepository, times(1)).save(any()); // Verify that userRepository.save was called exactly once
        }
    }
}