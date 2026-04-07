package pl.kaminski.forum.users.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.kaminski.forum.commons.DateTimeProvider;
import pl.kaminski.forum.users.application.IUserRepository;


@RequiredArgsConstructor
public class UserFactory {

    private final IUserRepository IUserRepository;
    private final DateTimeProvider dateTimeProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

}
