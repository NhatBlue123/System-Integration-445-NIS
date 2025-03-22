/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package springapp.web.controller;

import java.util.Base64;

/**
 *
 * @author Bluez
 */
public class TokenUtil {
  
    public static String generateToken(String username) {
        long timestamp = System.currentTimeMillis();
        String rawToken = username + "|" + timestamp;
        return Base64.getEncoder().encodeToString(rawToken.getBytes());
    }

    public static String decodeToken(String token) {
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        return new String(decodedBytes);
    }

}