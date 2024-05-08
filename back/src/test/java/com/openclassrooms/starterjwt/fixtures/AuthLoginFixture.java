package com.openclassrooms.starterjwt.fixtures;

import com.openclassrooms.starterjwt.payload.request.LoginRequest;

public class AuthLoginFixture {

    public static LoginRequest testLoginRequest() {
        LoginRequest newLoginRequest = new LoginRequest();
        newLoginRequest.setEmail("yoga@studio.com");
        newLoginRequest.setPassword("test!1234");

        return newLoginRequest;
    }
}
