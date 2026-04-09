package pl.kaminski.forum.users.infrastructure;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.users.query.IUserQueryRepository;
import pl.kaminski.forum.users.query.UserQuery;

import java.util.Optional;

@RequiredArgsConstructor
class UserQueryRepository implements IUserQueryRepository {

    private final UserQueryJpaRepository userQueryJpaRepository;

    @Override
    public Optional<UserQuery> findByUsername(String username) {
        return userQueryJpaRepository.findByUsername(username);
    }
}
