package org.example.projectapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.projectapi.enums.StatusDish;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Price cannot be null")
    private Double price;

    @Column(nullable = false)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull(message = "Category cannot be null")
    @JsonBackReference
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Status cannot be null")
    private StatusDish status = StatusDish.available;

    @Column(nullable = false)
    @NotBlank(message = "Discount cannot be blank")
    private String discount;

    public Dish() {
    }
}
