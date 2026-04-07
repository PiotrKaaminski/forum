package pl.kaminski.forum.users.infrastructure;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.users.application.IUserRepository;
import pl.kaminski.forum.users.domain.User;
import pl.kaminski.forum.users.domain.UsernameVO;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepository implements IUserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<EntityId> findIdByUsername(UsernameVO usernameVo) {
        return userJpaRepository.findIdByUsername(usernameVo);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername_Value(username);
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(user);
    }
}
