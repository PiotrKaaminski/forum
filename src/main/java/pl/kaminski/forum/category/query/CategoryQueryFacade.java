package pl.kaminski.forum.category.query;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.query.contract.ICategoryQueryFacade;

@RequiredArgsConstructor
public class CategoryQueryFacade implements ICategoryQueryFacade {

    private final ICategoryQueryRepository categoryQueryRepository;
}
