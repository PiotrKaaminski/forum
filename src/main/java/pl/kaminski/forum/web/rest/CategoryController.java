package pl.kaminski.forum.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kaminski.forum.category.application.contract.*;
import pl.kaminski.forum.commons.EntityId;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping("/category")
    CreateCategoryResult createCategory(@RequestBody CreateCategoryRequest request, Principal principal) {
        return categoryService.createCategory(request, principal.getName());
    }

    @PatchMapping("/category/{id}")
    ModifyCategoryResult modifyCategory(@PathVariable UUID id, @RequestBody ModifyCategoryRequest request) {
        return categoryService.modifyCategory(EntityId.from(id), request);
    }
}
