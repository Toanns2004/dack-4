package org.example.projectapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.projectapi.enums.StatusDish;

@Getter
@Setter
@AllArgsConstructor
public class DishRequest {
    private String name;
    private double price;
    private String image;
    private Long categoryId;
    private StatusDish status = StatusDish.available;
    private String discount;

    public DishRequest() {

    }
}
