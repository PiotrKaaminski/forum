package pl.kaminski.forum.category.query;

import java.util.Optional;
import java.util.UUID;

public interface ICategoryQueryRepository {
    Optional<QCategory> findById(UUID id);
}
