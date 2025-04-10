package com.civiliansconnection.civ.security.filters;

import com.civiliansconnection.civ.security.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final UserDetailsService userDetailsService;
    private final RequestMatcher ignoredPaths = new AntPathRequestMatcher("/authenticate");

    public JwtRequestFilter(JwtProvider jwtProvider, UserDetailsService userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (this.ignoredPaths.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String requestTokenHeader = request.getHeader("Authorization");

        Cookie cookie = Arrays.stream(request.getCookies())
                .filter(c -> "username".equals(c.getName()))
                .findAny()
                .orElse(null);
        System.out.println(request.getCookies()[0].getName());
        System.out.println(request.getCookies()[0].getValue());

        String username = null;

        String jwt = null;

        if (cookie != null && cookie.getName().equals("username")) {
            jwt = cookie.getValue();
            try {
                username = jwtProvider.extractUsername(jwt);
            } catch (IllegalArgumentException e) {
                log.info("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                log.info("JWT Token has expired");
            }
        } else {
            log.info("JWT Token does not begin with Bearer String");
        }

//        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
//            jwt = requestTokenHeader.substring(7);
//            try {
//                username = jwtProvider.extractUsername(jwt);
//            } catch (IllegalArgumentException e) {
//                log.info("Unable to get JWT Token");
//            } catch (ExpiredJwtException e) {
//                log.info("JWT Token has expired");
//            }
//        } else {
//            log.info("JWT Token does not begin with Bearer String");
//        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtProvider.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}

