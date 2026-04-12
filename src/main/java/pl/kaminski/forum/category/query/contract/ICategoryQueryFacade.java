package pl.kaminski.forum.category.query.contract;

import java.util.UUID;

public interface ICategoryQueryFacade {
    Boolean categoryExists(UUID categoryId);
}
