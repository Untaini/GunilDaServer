package com.walkingtalking.gunilda.auth.interceptorr;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.walkingtalking.gunilda.auth.dto.JwtTokenDTO;
import com.walkingtalking.gunilda.auth.exception.AuthException;
import com.walkingtalking.gunilda.auth.exception.type.AuthExceptionType;
import com.walkingtalking.gunilda.auth.provider.JwtProvider;
import com.walkingtalking.gunilda.base.BaseExceptionDTO;
import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = extractToken(request, "Bearer");

        try {
            if (token.isEmpty()) {
                throw new AuthException(AuthExceptionType.TOKEN_NOT_FOUND);
            }

            JwtTokenDTO.TokenPayload payload = jwtProvider.verify(token);

            request.setAttribute("userId", payload.userId());

        } catch (AuthException ae) {
            BaseExceptionDTO exceptionDTO = BaseExceptionDTO.from(ae);
            String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(exceptionDTO);

            response.setStatus(ae.getExceptionType().getHttpStatus().value());
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(body);

            return false;
        }

        return true;
    }

    private String extractToken(HttpServletRequest request, String type) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);

        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value.toLowerCase().startsWith(type.toLowerCase())) {
                return value.substring(type.length()).trim();
            }
        }

        return Strings.EMPTY;
    }

}
