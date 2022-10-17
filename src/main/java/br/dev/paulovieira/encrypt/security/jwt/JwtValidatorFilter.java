package br.dev.paulovieira.encrypt.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtValidatorFilter extends BasicAuthenticationFilter {

    public static final String HEADER_ATTR = "Authorization";
    public static final String ATTR_PREFIX = "Bearer ";

    public JwtValidatorFilter(AuthenticationManager authenticationManager) {

        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        var header = request.getHeader(HEADER_ATTR);

        if (header == null || !header.startsWith(ATTR_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        var token = header.replace(ATTR_PREFIX, "");
        var authentication = getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        var user = JWT.require(Algorithm.HMAC512(JwtAuthenticationFilter.SECRET))
                .build()
                .verify(token)
                .getSubject();

        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        }

        return null;
    }
}
