package pl.kaminski.forum.users.application.contract;

public interface IUserService {

    RegisterUserResult registerNewUser(RegisterUserRequest request);

}
