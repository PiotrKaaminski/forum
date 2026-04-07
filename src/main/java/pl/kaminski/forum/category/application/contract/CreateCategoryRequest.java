package pl.kaminski.forum.category.application.contract;

import java.util.UUID;

public record CreateCategoryRequest(String name, UUID parentId) {
}
