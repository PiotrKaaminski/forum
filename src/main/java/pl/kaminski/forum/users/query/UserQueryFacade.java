package pl.kaminski.forum.users.query;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.users.query.contract.IUserQueryFacade;
import pl.kaminski.forum.users.query.contract.UserInfo;

@RequiredArgsConstructor
public class UserQueryFacade implements IUserQueryFacade {

    private final IUserQueryRepository userQueryRepository;

    @Override
    public UserInfo getMe(String username) {
        return userQueryRepository.findByUsername(username)
                .map(u -> new UserInfo(
                        u.getId(),
                        u.getUsername(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getRole()
                ))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
