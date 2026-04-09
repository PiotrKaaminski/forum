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
                        u.getId().value(),
                        u.getUsername().getUsername(),
                        u.getFirstName().getFirstName(),
                        u.getLastName().getLastName(),
                        u.getRole()
                ))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
