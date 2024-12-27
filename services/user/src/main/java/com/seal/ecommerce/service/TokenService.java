package com.seal.ecommerce.service;

import com.seal.ecommerce.entity.Token;
import com.seal.ecommerce.entity.User;

public interface TokenService {
    Token getByToken(String token);

    Token save(Token authToken);
    String generateAndSaveActivationToken(User user);
}
