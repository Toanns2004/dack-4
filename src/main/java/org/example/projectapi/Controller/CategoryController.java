package org.example.projectapi.Controller;

import org.example.projectapi.Service.CategoryService;
import org.example.projectapi.dto.request.CategoryRequest;
import org.example.projectapi.dto.response.MessageRespone;
import org.example.projectapi.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategory() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.findCategoryId(id);
        return ResponseEntity.ok().body(category);
    }

    @PostMapping
    public ResponseEntity<MessageRespone> createCategory(@RequestBody Category category) {
         categoryService.save(category);
         return ResponseEntity.ok().body(new MessageRespone("Category created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageRespone> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryDto) {
       Category category = categoryService.findCategoryId(id);

       category.setName(categoryDto.getName());
       categoryService.save(category);
       return ResponseEntity.ok().body(new MessageRespone("Category updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageRespone> deleteCustomer(@PathVariable Long id) {
        Category category = categoryService.findCategoryId(id);
        if (category != null) {
            categoryService.deleteById(category.getId());
            return ResponseEntity.ok().body(new MessageRespone("Category deleted successfully"));
        }
        return ResponseEntity.ok().body(new MessageRespone("Category not found"));
    }


}
