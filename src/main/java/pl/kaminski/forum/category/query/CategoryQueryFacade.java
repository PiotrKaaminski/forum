package pl.kaminski.forum.category.query;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.kaminski.forum.category.query.contract.ICategoryQueryFacade;

import java.util.UUID;

@RequiredArgsConstructor
public class CategoryQueryFacade implements ICategoryQueryFacade {

    private final ICategoryQueryRepository categoryQueryRepository;

    @Override
    @Transactional(readOnly = true)
    public Boolean categoryExists(UUID categoryId) {
        return categoryQueryRepository.findById(categoryId).isPresent();
    }
}
