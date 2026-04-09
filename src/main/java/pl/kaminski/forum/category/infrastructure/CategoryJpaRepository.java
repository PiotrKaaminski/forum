package pl.kaminski.forum.category.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kaminski.forum.category.domain.Category;
import pl.kaminski.forum.category.domain.CategoryNameVO;
import pl.kaminski.forum.commons.EntityId;

import java.util.Optional;

@Repository
interface CategoryJpaRepository extends JpaRepository<Category, EntityId> {

    @Query("SELECT c.id FROM Category c WHERE c.name = :categoryNameVO AND c.parentId = :parentId")
    Optional<EntityId> findIdByNameAndParentId(CategoryNameVO categoryNameVO, EntityId parentId);

    @Query("SELECT c.id FROM Category c WHERE c.name = :categoryNameVO and c.parentId is null")
    Optional<EntityId> findIdByNameAndNullParent(CategoryNameVO categoryNameVO);
}
