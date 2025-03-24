package com.toilamdev.stepbystep.filters;

import com.toilamdev.stepbystep.utils.JWTokenUtils;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTokenFilter extends OncePerRequestFilter {
    private final JWTokenUtils jwTokenUtils;
    private final UserDetailsService userDetailsService;

    @Value("${api.version:/api/v1}")
    private String apiVersion;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if(isByPassToken(request) || authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        try{
            final String token = authHeader.substring(7);
            final String email = jwTokenUtils.extractEmail(token);

            if (email != null && !email.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if (jwTokenUtils.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }catch (Exception e) {
            log.error("JWT processing failed: {}", e.getMessage(), e);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isByPassToken(HttpServletRequest request) {
        Set<String> byPassTokens = Set.of(
                String.format("%s/auth/login:POST", apiVersion),
                String.format("%s/auth/register:POST", apiVersion)
        );

        return byPassTokens.contains(request.getServletPath() + ":" + request.getMethod());
    }
}
