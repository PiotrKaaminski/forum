package pl.kaminski.forum.users.application.contract;

import pl.kaminski.forum.commons.EntityId;

import java.util.Optional;

public interface IUserService {

    RegisterUserResult registerNewUser(RegisterUserRequest request);
    Optional<EntityId> getIdByUsername(String username);
}
