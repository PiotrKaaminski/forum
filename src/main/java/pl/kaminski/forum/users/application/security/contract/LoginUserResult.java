package pl.kaminski.forum.users.application.security.contract;

import pl.kaminski.forum.commons.result.Result;
import pl.kaminski.forum.commons.result.ResultError;

public class LoginUserResult extends Result<LoginUserResult.Success, LoginUserResult.BadCredentials> {

    private LoginUserResult(Success success) {super(success);}
    private LoginUserResult(BadCredentials error) {super(error);}

    public static LoginUserResult success(String jwtToken) {return new LoginUserResult(new Success(jwtToken));}
    public static LoginUserResult badCredentials() {return new LoginUserResult(new BadCredentials());}

    public record Success(String JwtToken) {}
    public record BadCredentials() implements ResultError {
        @Override
        public String getMessage() {
            return "Username or password is incorrect";
        }
    }
}
