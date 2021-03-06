package am.aca.courses.security;

import am.aca.courses.exceptions.JwtAuthenticationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter for authorization token.
 *
 * @author Arthur
 * @version 1.0
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {


    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    @Value("${jwt.header}")
    private String authorizationHeader;

    public JwtTokenFilter(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                          JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String tokenHeader = request.getHeader(authorizationHeader);

        String username = null;
        String jwtToken = null;
        try {
            if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
                jwtToken = tokenHeader.substring(7);
                username = jwtTokenProvider.getUsernameFromToken(jwtToken);
            }
            if (username != null) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String sessionUserName = authentication != null ? authentication.getName() : null;
                if (!jwtTokenProvider.validateToken(jwtToken, sessionUserName)) {
                    throw new JwtAuthenticationException("JWT token is expired or invalid");
                }
                if (authentication == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (JwtAuthenticationException e) {
            SecurityContextHolder.clearContext();
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
        filterChain.doFilter(request, response);
    }
}
