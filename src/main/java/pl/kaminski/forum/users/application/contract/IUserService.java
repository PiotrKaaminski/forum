package pl.kaminski.forum.users.application.contract;

import pl.kaminski.forum.users.domain.User;

import java.util.Optional;

public interface IUserService {

    RegisterUserResult registerNewUser(RegisterUserRequest request);
    Optional<User> getByUsername(String username);
}
