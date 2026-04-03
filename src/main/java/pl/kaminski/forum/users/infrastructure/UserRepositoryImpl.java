package pl.kaminski.forum.users.infrastructure;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.users.application.UserRepository;
import pl.kaminski.forum.users.domain.User;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Boolean isUsernameNotUnique(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public void save(User user) {

    }
}
