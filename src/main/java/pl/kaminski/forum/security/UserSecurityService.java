package pl.kaminski.forum.security;

public interface UserSecurityService {
    String authenticate(String username, String password);
}
