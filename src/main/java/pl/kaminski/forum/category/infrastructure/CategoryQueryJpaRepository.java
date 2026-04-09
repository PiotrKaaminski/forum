package pl.kaminski.forum.category.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kaminski.forum.category.query.CategoryQuery;

import java.util.UUID;

@Repository
interface CategoryQueryJpaRepository extends JpaRepository<CategoryQuery, UUID> {
}
