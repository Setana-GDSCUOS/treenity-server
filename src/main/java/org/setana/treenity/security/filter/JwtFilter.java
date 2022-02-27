package org.setana.treenity.security.filter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.setana.treenity.security.util.RequestUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        FirebaseToken decodedToken = null;

        try {
            String header = RequestUtil.getAuthorizationToken(request.getHeader("Authorization"));
            decodedToken = firebaseAuth.verifyIdToken(header);

        } catch (IllegalArgumentException | FirebaseAuthException e) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter()
                .write("{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
        }

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(decodedToken.getUid());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (UsernameNotFoundException e) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"code\":\"USER_NOT_FOUND\"}");
        }

        filterChain.doFilter(request, response);
    }
}
