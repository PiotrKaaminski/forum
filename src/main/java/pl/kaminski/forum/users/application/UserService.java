package pl.kaminski.forum.users.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kaminski.forum.api.users.RegisterUserRequest;
import pl.kaminski.forum.users.domain.PersonalInfoVO;
import pl.kaminski.forum.users.domain.User;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registerNewUser(RegisterUserRequest request) {
        var personalInfo = PersonalInfoVO.create(request.firstName(), request.lastName(), request.birthDate());
        var user = User.create(request.username(), request.password(), personalInfo, request.role());

        // walidacja Uniqueness username
    }
}
