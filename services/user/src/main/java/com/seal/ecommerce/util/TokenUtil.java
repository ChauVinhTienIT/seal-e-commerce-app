package com.seal.ecommerce.util;

import java.security.SecureRandom;

public class TokenUtil {
    public static String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i = 0; i < length; i++){
            int randomIndex = secureRandom.nextInt(characters.length()); // Random from 0 to 9
            codeBuilder.append(characters.charAt(randomIndex)); // append to return value
        }
        return codeBuilder.toString();
    }
}
