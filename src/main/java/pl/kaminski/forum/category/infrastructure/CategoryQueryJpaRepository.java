package pl.kaminski.forum.category.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kaminski.forum.category.query.QCategory;

import java.util.UUID;

@Repository
interface CategoryQueryJpaRepository extends JpaRepository<QCategory, UUID> {
}
