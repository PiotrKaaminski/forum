package pl.kaminski.forum.users.domain;

public interface IPasswordHistory {

    boolean passwordWasUsed(String password);
}
