package pl.kaminski.forum.users.application.contract.authentication;

public interface IAuthenticationService {
    LoginUserResult login(LoginUserRequest request);
}
