package com.eduardopontes.romaneioapp.security.jwt;

import com.eduardopontes.romaneioapp.dto.JwtDto;
import com.eduardopontes.romaneioapp.dto.UserResumeDto;
import com.eduardopontes.romaneioapp.dto.mapper.UserResumeMapper;
import com.eduardopontes.romaneioapp.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JWTService {

    private final UserResumeMapper userResumeMapper;

    private final SecretKey signatureKey;

    @Value("${security.jwt.expiry}")
    private int expiry;

    public JWTService(UserResumeMapper userResumeMapper) {
        this.signatureKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        this.userResumeMapper = userResumeMapper;
    }

    public JwtDto tokenGenerate(User user) {
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(expiry);
        Instant instant = expiryDate.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);


        UserResumeDto userDto = userResumeMapper.fromUser(user);

        String token = Jwts
                .builder()
                .setSubject(user.getNickname())
                .claim("user", userDto)
                .signWith(signatureKey)
                .setExpiration(date)
                .compact();

        return JwtDto.builder()
                .token(token)
                .prefix("Bearer")
                .expiry(instant.toEpochMilli())
                .build();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {

        return Jwts
                .parserBuilder()
                .setSigningKey(signatureKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValid(String token) {
        try {
            Claims claims = getClaims(token);
            Date expiration = claims.getExpiration();
            LocalDateTime localDateTime =
                    expiration.toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(localDateTime);
        } catch (Exception e) {
            return false;
        }
    }

    public String getSubject(String token) throws ExpiredJwtException {
        return getClaims(token).getSubject();
    }
}
