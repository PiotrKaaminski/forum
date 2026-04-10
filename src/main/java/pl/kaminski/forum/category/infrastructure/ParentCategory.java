package pl.kaminski.forum.category.infrastructure;

import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.category.domain.IParentCategory;
import pl.kaminski.forum.commons.EntityId;

import java.util.UUID;

@RequiredArgsConstructor
class ParentCategory implements IParentCategory {
    private final EntityId categoryId;
}
