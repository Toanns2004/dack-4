package org.example.projectapi.Controller;

import org.example.projectapi.Service.CategoryService;
import org.example.projectapi.Service.DishService;
import org.example.projectapi.dto.response.MessageRespone;
import org.example.projectapi.enums.StatusDish;
import org.example.projectapi.model.Category;
import org.example.projectapi.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/dishes")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<Dish>> getFoods(Pageable pageable) {
        Page<Dish> foods = dishService.getAllDish(pageable);
        return ResponseEntity.ok(foods);
    }

    @PostMapping
    public ResponseEntity<?> createFood(
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("discount") String discount,
            @RequestParam("category_id") Long categoryId,
            @RequestParam("image") MultipartFile image) {

        try {
            // Xử lý file hình ảnh
            String imageName = null;
            if (image != null && !image.isEmpty()) {
                // Lưu file hình ảnh vào thư mục
                String originalFilename = image.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String uniqueFilename = new Date().getTime() + fileExtension;
                String imagePath = "public/img/" + uniqueFilename;

                File uploadDirectory = new File("public/img");
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdirs();
                }

                Files.copy(image.getInputStream(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
                imageName = uniqueFilename;
            }




            Category category = categoryService.findCategoryId(categoryId);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageRespone("Category not found"));
            }

            Dish newDish = new Dish();
            newDish.setName(name);
            newDish.setPrice(price);
            newDish.setDiscount(discount);
            newDish.setCategory(category);
            newDish.setImage(imageName);

            Dish savedDish = dishService.save(newDish);



            return ResponseEntity.status(HttpStatus.CREATED).body(savedDish);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageRespone("Error: " + e.getMessage()));
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<MessageRespone> updateFood(@PathVariable Long id,
                                                     @RequestParam("name") String name,
                                                     @RequestParam("price") Double price,
                                                     @RequestParam(value = "image") MultipartFile image,
                                                     @RequestParam("discount") String discount,
                                                     @RequestParam("status_dish") StatusDish statusDish,
                                                     @RequestParam("category_id") Long categoryId) {
        try {
            Dish updateDish = dishService.findById(id);
            if (updateDish != null){

                if (image != null) {
                    String oldImageName = updateDish.getImage();
                    if (oldImageName != null) {
                        String imagePath = "public/img/" + oldImageName;
                        File oldImageFile = new File(imagePath);
                        if (oldImageFile.exists()) {
                            oldImageFile.delete();
                        }
                    }

                    File uploadDirectory = new File("public/img");
                    if (!uploadDirectory.exists()) {
                        uploadDirectory.mkdirs();
                    }

                    String originalFilename = image.getOriginalFilename();
                    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String uniqueFilename = new Date().getTime() + fileExtension;

                    String newImagePath = "public/img/" + uniqueFilename;
                    Files.copy(
                            image.getInputStream(),
                            Paths.get(newImagePath),
                            StandardCopyOption.REPLACE_EXISTING
                    );
                    updateDish.setImage(uniqueFilename);
                }

                Category category = categoryService.findCategoryId(categoryId);
                if (category != null) {
                    updateDish.setCategory(category);
                }
                updateDish.setName(name);
                updateDish.setPrice(price);
                updateDish.setDiscount(discount);
                updateDish.setStatus(statusDish);
                dishService.update(updateDish);


                return ResponseEntity.ok().body(new MessageRespone("Updated successfully"));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageRespone("Food not found"));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageRespone("Failed to update food: " + e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageRespone> changeStatusDish(@PathVariable long id, @RequestParam StatusDish status) {
        Dish updateDish = dishService.findById(id);
        if (updateDish != null) {
            updateDish.setStatus(status);
            dishService.update(updateDish);
            return ResponseEntity.ok().body(new MessageRespone("Change status successfully"));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageRespone("Food not found"));
        }
    }
}
