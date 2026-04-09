package pl.kaminski.forum.users.query.contract;

public interface IUserQueryFacade {

    UserInfo getMe(String username);
}
