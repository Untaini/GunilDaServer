package com.walkingtalking.gunilda.auth.provider;

import com.walkingtalking.gunilda.auth.dto.JwtTokenDTO;
import com.walkingtalking.gunilda.auth.entity.AccessTokenBlackList;
import com.walkingtalking.gunilda.auth.entity.RefreshToken;
import com.walkingtalking.gunilda.auth.exception.AuthException;
import com.walkingtalking.gunilda.auth.exception.type.AuthExceptionType;
import com.walkingtalking.gunilda.auth.repository.AccessTokenBlackListWithRedis;
import com.walkingtalking.gunilda.auth.repository.RefreshTokenRepositoryWithRedis;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
@PropertySource("classpath:jwt.properties")
public class JwtProvider {

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000L; //1시간
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000L; //7일

    private final RefreshTokenRepositoryWithRedis tokenRepository;
    private final AccessTokenBlackListWithRedis blackListRepository;
    private SecretKey secretKey;
    private JwtParser jwtParser;

    @Autowired
    public JwtProvider(@Value("${jwt.secret_key}") String key,
                       RefreshTokenRepositoryWithRedis tokenRepository,
                       AccessTokenBlackListWithRedis blackListRepository) {

        //설정한 key를 base64로 인코딩하고 HMAC-SHA로 암호화한다.
        this.secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(key.getBytes()).getBytes());
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
        this.tokenRepository = tokenRepository;
        this.blackListRepository = blackListRepository;
    }

    @Transactional
    public JwtTokenDTO.GeneratingResponse generateToken(JwtTokenDTO.GeneratingWithIdRequest request) {
        return generateToken(request.userId());
    }

    @Transactional
    public JwtTokenDTO.GeneratingResponse generateToken(JwtTokenDTO.GeneratingWithRefreshTokenRequest request) {
        if (!tokenRepository.existsById(request.refreshToken())) {
            throw new AuthException(AuthExceptionType.EXPIRED_REFRESH_TOKEN);
        }

        RefreshToken token = tokenRepository.findById(request.refreshToken()).get();

        return generateToken(token.getUserId());
    }

    public JwtTokenDTO.TokenPayload verify(String accessToken) {
        try {
            Claims claims = jwtParser.parseSignedClaims(accessToken).getPayload();

            return JwtTokenDTO.TokenPayload.builder()
                    .userId(Long.parseLong(claims.get("userId").toString()))
                    .issuedAt(claims.getIssuedAt())
                    .expiration(claims.getExpiration())
                    .build();

        } catch (ExpiredJwtException eje) {
            throw new AuthException(AuthExceptionType.EXPIRED_ACCESS_TOKEN);
        } catch (SignatureException | NumberFormatException e) {
            throw new AuthException(AuthExceptionType.INVALID_TOKEN);
        }
    }

    private JwtTokenDTO.GeneratingResponse generateToken(Long userId) {
        JwtTokenDTO.GeneratingResponse response = JwtTokenDTO.GeneratingResponse.builder()
                .accessToken(generateAccessToken(userId))
                .refreshToken(generateRefreshToken())
                .build();

        RefreshToken token = RefreshToken.builder()
                .refreshToken(response.refreshToken())
                .accessToken(response.accessToken())
                .userId(userId)
                .ttl(REFRESH_TOKEN_EXPIRATION_TIME)
                .build();

        removePreviousToken(userId);

        tokenRepository.save(token);

        return response;
    }

    private String generateAccessToken(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    private String generateRefreshToken() {
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    private void removePreviousToken(Long userId) {
        if (!tokenRepository.existsByUserId(userId)) {
            return;
        }

        RefreshToken token = tokenRepository.findByUserId(userId).get();

        // 중복 로그인 방지를 위한 코드
        // refresh token을 명시적으로 삭제한다는 것은 중복 로그인이 되었다는 의미임
        // 따라서 현재 이용 가능한 access token을 블랙리스트로 만들어 이전에 로그인 했던 사용자의 접근을 차단함
        try {
            String accessTokenForBlackList = token.getAccessToken();
            Long ttl = verify(accessTokenForBlackList).expiration().getTime() - System.currentTimeMillis();

            AccessTokenBlackList blackListToken = AccessTokenBlackList.builder()
                    .accessToken(accessTokenForBlackList)
                    .ttl(ttl) //블랙리스트 기간을 토큰 만료 기간과 동일하게 잡음
                    .build();

            blackListRepository.save(blackListToken);
        } catch (AuthException ae) {}//access token을 삭제할 필요가 없음.

        tokenRepository.delete(token);
    }

}
