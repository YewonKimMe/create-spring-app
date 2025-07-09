package com.github.YewonKimMe.create_spring_app.security.utils;

import com.github.YewonKimMe.create_spring_app.security.enums.*;
import com.github.YewonKimMe.create_spring_app.security.exception.TokenStorageException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.github.YewonKimMe.create_spring_app.security.enums.JwtClaimKey.*;

@Component
public class JwtProvider implements TokenProvider {

    @Value("${spring.application.name}")
    private String APP_NAME;

    private final SecretKey secretKey;

    private final TokenStore tokenStore;

    public JwtProvider(@Value("${spring.security.auth.jwt.secret}") String JWT_SECRET, TokenStore tokenStore) {
        this.secretKey = getSecretKey(JWT_SECRET);
        this.tokenStore = tokenStore;
    }

    public SecretKey getSecretKey(String JWT_SECRET) {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateToken(Authentication auth, TokenType tokenType, long duration) {
        return buildToken(auth, tokenType, duration);
    }

    private String buildToken(Authentication auth, TokenType tokenType, long duration) {

        String username = auth.getName();
        String authorities = AuthorityUtils.populateAuthorities(auth.getAuthorities());
        Instant expirationTime = Instant.now().plus(Duration.ofHours(duration));

        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(expirationTime))
                .claim(TYPE.getClaimKey(), tokenType.getTokenType())
                .claim(PROVIDER.getClaimKey(), APP_NAME)
                .claim(AUTHORITIES.getClaimKey(), authorities)
                .signWith(secretKey)
                .compact();
    }

    @Override
    public TokenValidationResult validateToken(String token) {

        if (token == null || token.isEmpty()) {
            return TokenValidationResult.EMPTY;
        }

        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return TokenValidationResult.VALID;

        } catch (ExpiredJwtException e) {
            return TokenValidationResult.EXPIRED;

        } catch (MalformedJwtException e) {
            return TokenValidationResult.MALFORMED;

        } catch (SignatureException e) {
            return TokenValidationResult.INVALID_SIGNATURE;

        } catch (UnsupportedJwtException e) {
            return TokenValidationResult.UNSUPPORTED;

        } catch (IllegalArgumentException e) {
            return TokenValidationResult.INVALID;

        } catch (Exception e) {
            return TokenValidationResult.UNKNOWN;
        }
    }

    @Override
    public Authentication getAuthentication(String token) {
        Claims claim = getClaim(token);

        if (claim == null) return null;

        String username = claim.get(SUBJECT.getClaimKey(), String.class);

        String authorities = claim.get(AUTHORITIES.getClaimKey(), String.class);

        List<GrantedAuthority> grantedAuthorities = org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

        UserDetails userInfo = new User(username, "", grantedAuthorities);

        return new UsernamePasswordAuthenticationToken(userInfo, "", grantedAuthorities);
    }

    private Claims getClaim(String jwtToken) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    @Override
    public String getUsername(String token) {
        Claims claim = getClaim(token);

        return claim.get(SUBJECT.getClaimKey(), String.class);
    }

    @Override
    public List<String> getAuthorities(String token) {
        Claims claim = getClaim(token);

        String authorities = (String)claim.get(AUTHORITIES.getClaimKey());

        if (authorities == null || authorities.isBlank()) {
            return List.of();
        }

        return Arrays.stream(authorities.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Override
    public String resolveToken(HttpServletRequest req) {
        String authHeader = req.getHeader(SecurityConst.AUTH_HEADER.getValue());

        if (StringUtils.hasText(authHeader) && authHeader.startsWith(SecurityConst.BEARER_PREFIX.getValue())) {
            return authHeader.substring(SecurityConst.BEARER_PREFIX.getValue().length());
        }

        return null;
    }

    @Override
    public String generateRefreshToken(Authentication auth) {
        return buildToken(auth, TokenType.REFRESH, TokenDurationTime.REFRESH.getTime());
    }

    @Override
    public void saveRefreshToken(String redisKey, String refreshToken) {
        try {
            // 기존 토큰 덮어쓰기 + TTL 갱신
            tokenStore.save(redisKey, refreshToken, TokenDurationTime.REFRESH.getTime(), TimeUnit.HOURS);
        } catch (DataAccessException ex) {
            throw new TokenStorageException("Exception: failed to save token", ex);
        }
    }

    @Override
    public String getRefreshToken(String redisKey) {
        Optional<String> opt = tokenStore.get(redisKey);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new TokenStorageException("Refresh Token not found: " + redisKey);
    }

    @Override
    public TokenValidationResult validateRefreshToken(String refreshToken) {
        return this.validateToken(refreshToken);
    }

    @Override
    public void addBlackList(@NotNull Authentication auth, @NotNull String token) {

        if (auth == null || token == null) {
            throw new IllegalArgumentException("auth나 token은 null 일 수 없습니다.");
        }

        String blackListKey = getBlackListKey(auth, SecurityRedisKey.BLACK_LIST);

        // 남은 시간 계산
        long remainingTime = calcRemainingTime(token);

        if (remainingTime == 0L) {
            return;
        }

        tokenStore.save(blackListKey, token, remainingTime, TimeUnit.MILLISECONDS);
    }

    private long calcRemainingTime(String token) {

        try {
            Claims claim = this.getClaim(token);

            Date expiration = claim.getExpiration();
            long now = Instant.now().toEpochMilli();

            return expiration.getTime() - now;
        } catch (ExpiredJwtException e) {
            return 0L;
        }
    }

    @Override
    public void deleteBlackList(Authentication auth, String token) {
        String blackListKey = this.getBlackListKey(auth, SecurityRedisKey.BLACK_LIST);

        tokenStore.remove(blackListKey);
    }

    @NotNull
    private String getBlackListKey(Authentication auth, SecurityRedisKey type) {
        return SecurityRedisKey.generateKey(auth.getName(), type);
    }
}
