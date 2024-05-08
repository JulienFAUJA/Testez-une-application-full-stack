package com.openclassrooms.starterjwt.fixtures;

import com.openclassrooms.starterjwt.payload.request.SignupRequest;

public class AuthSignupRequestTest {
    public static SignupRequest testSignUpRequest() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("yoga@test.com");
        signupRequest.setFirstName("admin");
        signupRequest.setLastName("admin");
        signupRequest.setPassword("mot de passe");

        return signupRequest;
    }

    public static SignupRequest testSignUpRequestFakeEmail() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("toto4@test.com");
        signupRequest.setFirstName("admin");
        signupRequest.setLastName("admin");
        signupRequest.setPassword("mot de passe");

        return signupRequest;
    }
}
