package com.example.pizzaapplication.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtils {
    private static final String USER_ID = "nameid";

    public static DecodedJWT decodeJWT(String token) {

        return JWT.decode(token);
    }
    public static String getUserId(String token) {
        DecodedJWT decodedJWT = decodeJWT(token);
        return decodedJWT.getClaim(USER_ID).asString();
    }

}
