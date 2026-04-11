package pl.kaminski.forum.users.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.kaminski.forum.commons.EntityId;
import pl.kaminski.forum.users.domain.IPasswordHistory;

@RequiredArgsConstructor
class PasswordHistory implements IPasswordHistory {

    private final EntityId userId;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean passwordWasUsed(String password) {
        return false;
    }
}
