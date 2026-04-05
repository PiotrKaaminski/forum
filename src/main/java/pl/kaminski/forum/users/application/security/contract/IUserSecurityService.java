package pl.kaminski.forum.users.application.security.contract;

import pl.kaminski.forum.users.domain.security.contract.LoginUserRequest;

public interface IUserSecurityService {
    LoginUserResult login(LoginUserRequest request);
}
