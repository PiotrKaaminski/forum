package pl.kaminski.forum.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service // do zmiany
public class UserSecurityServiceImpl implements UserSecurityService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public String authenticate(String username, String password) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtUtils.generateToken(auth.getName());
    }
}
