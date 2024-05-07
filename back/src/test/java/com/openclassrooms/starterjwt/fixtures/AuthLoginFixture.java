package com.openclassrooms.starterjwt.fixtures;

import com.openclassrooms.starterjwt.payload.request.LoginRequest;

public class AuthLoginFixture {

    public static LoginRequest testLoginRequest() {
        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setEmail("yoga@test.com");
        newLoginRequest.setPassword("test123");

        return newLoginRequest;
    }
}
