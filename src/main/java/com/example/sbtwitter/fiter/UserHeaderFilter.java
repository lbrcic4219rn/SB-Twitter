package com.example.sbtwitter.fiter;

import com.example.sbtwitter.exception.UnauthorisedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserHeaderFilter extends OncePerRequestFilter {

    private static final String PATTERN = "^[a-zA-Z0-9_]{4,32}$";
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws UnauthorisedException, IOException, ServletException {

        if(request.getHeader("X-Username") == null) {
            handleUnauthorized(response);
            return;
        }

        String username = request.getHeader("X-Username");

        if(!username.matches(PATTERN)) {
            handleUnauthorized(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void handleUnauthorized(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        UnauthorisedException ex = new UnauthorisedException();
        Map<String, Object> responseObject = new HashMap<>();
        response.setStatus(ex.getHttpCode());
        responseObject.put("httpCode", ex.getHttpCode());
        responseObject.put("errorCode", ex.getErrorCode());
        responseObject.put("message", ex.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(responseObject));
    }
}
