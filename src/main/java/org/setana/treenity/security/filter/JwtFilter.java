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
import org.setana.treenity.exception.NotFoundException;
import org.setana.treenity.security.util.RequestUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            // decode token
            String header = RequestUtil.getAuthorizationToken(request.getHeader("Authorization"));
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(header);

            // get user
            UserDetails userDetails = userDetailsService.loadUserByUsername(decodedToken.getUid());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (IllegalArgumentException | FirebaseAuthException e) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter()
                .write(
                    "{\"code\":\"INVALID_TOKEN\", \"message\":\"Invalid Authorization header.\"}");
            return;

        } catch (NotFoundException e) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter()
                .write("{\"code\":\"NOT_FOUND\", \"message\":\"Cannot find user.\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
