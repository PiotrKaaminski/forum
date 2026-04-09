package pl.kaminski.forum.users.infrastructure;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.users.domain.IUserRepository;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.users.domain.User;
import pl.kaminski.forum.users.domain.UsernameVO;
import pl.kaminski.forum.users.query.IUserQueryRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepository implements IUserRepository, IUserQueryRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public boolean existsByUsername(UsernameVO usernameVo) {
        return userJpaRepository.existsByUsername(usernameVo);
    }

    @Override
    public Optional<EntityId> findIdByUsername(String username) {
        return userJpaRepository.findIdByUsername(username);
    }


    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername_Username(username);
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(user);
    }
}
