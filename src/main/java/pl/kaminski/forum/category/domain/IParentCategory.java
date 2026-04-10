package pl.kaminski.forum.category.domain;

public interface IParentCategory {
    boolean subcategoryWithNameExists(CategoryNameVO name);
}
