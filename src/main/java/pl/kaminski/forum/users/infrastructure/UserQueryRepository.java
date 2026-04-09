package pl.kaminski.forum.users.infrastructure;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.users.query.IUserQueryRepository;
import pl.kaminski.forum.users.query.User;

import java.util.Optional;

@RequiredArgsConstructor
class UserQueryRepository implements IUserQueryRepository {

    private final UserQueryJpaRepository userQueryJpaRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userQueryJpaRepository.findByUsername(username);
    }
}
