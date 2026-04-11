package pl.kaminski.forum.category.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kaminski.forum.category.domain.Category;
import pl.kaminski.forum.category.domain.CategoryNameVO;
import pl.kaminski.forum.category.domain.IParentCategory;
import pl.kaminski.forum.commons.EntityId;

@Repository
interface CategoryJpaRepository extends JpaRepository<Category, EntityId> {

    Boolean existsByNameAndParentCategory(CategoryNameVO categoryNameVO, IParentCategory parentCategory);

}