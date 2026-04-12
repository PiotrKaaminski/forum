package pl.kaminski.forum.users.infrastructure;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.kaminski.forum.commons.AuthenticatedUser;
import pl.kaminski.forum.users.application.JwtUtils;
import pl.kaminski.forum.users.domain.IUserRepository;

import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(token.substring(7));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (!jwtUtils.isTokenValid(token)) {
            return null;
        }
        var username = jwtUtils.extractUsername(token);
        if (username == null) {
            return null;
        }
        var userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return null;
        }
        var user = userOptional.get();
        var userRole = userOptional.get().getRole();
        var authenticatedUser = new AuthenticatedUser(user.getId(), user.getUsername().getUsername(), userRole);
        return new UsernamePasswordAuthenticationToken(authenticatedUser, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole)));
    }
}
