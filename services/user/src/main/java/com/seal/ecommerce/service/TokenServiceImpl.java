package com.seal.ecommerce.service;

import com.seal.ecommerce.entity.Token;
import com.seal.ecommerce.entity.User;
import com.seal.ecommerce.exception.AppException;
import com.seal.ecommerce.exception.ErrorCode;
import com.seal.ecommerce.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.seal.ecommerce.util.TokenUtil.generateActivationCode;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class TokenServiceImpl implements TokenService{
    TokenRepository tokenRepository;

    @Override
    public Token getByToken(String token) {
        return tokenRepository.findByToken(token)
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_FOUND));
    }

    @Override
    public Token save(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public String generateAndSaveActivationToken(User user) {
        String generateToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generateToken)
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
        tokenRepository.save(token);
        return generateToken;
    }
}
