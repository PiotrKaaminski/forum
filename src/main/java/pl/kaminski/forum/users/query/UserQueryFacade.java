package pl.kaminski.forum.users.query;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.kaminski.forum.users.query.contract.IUserQueryFacade;
import pl.kaminski.forum.users.query.contract.UserInfo;

@RequiredArgsConstructor
public class UserQueryFacade implements IUserQueryFacade {

    private final IUserQueryRepository userQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public UserInfo getMe(String username) {
        return userQueryRepository.findByUsername(username)
                .map(QUser::toUserInfo)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
