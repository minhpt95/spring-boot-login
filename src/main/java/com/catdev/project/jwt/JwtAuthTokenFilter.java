package com.catdev.project.jwt;

import com.catdev.project.respository.UserRepository;
import com.catdev.project.security.service.UserDetailsServiceImpl;
import com.catdev.project.security.service.UserPrinciple;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwt(request);
            if(jwt == null){
                filterChain.doFilter(request, response);
                return;
            }

            if(!tokenProvider.validateJwtToken(jwt)){
                log.error("Validate token failed");
                filterChain.doFilter(request,response);
                return;
            }

            String email = tokenProvider.getEmailFromJwtToken(jwt);

            UserPrinciple userDetails = userDetailsService.loadUserByUsername(email);

            if(!jwt.equals(userDetails.getAccessToken())){
                log.error("Wrong access token");
                filterChain.doFilter(request,response);
                return;
            }

            userDetails.setRemainTime(tokenProvider.getRemainTimeFromJwtToken(jwt));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.error("Can NOT set user authentication -> Message: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ","");
        }

        return null;
    }
}
