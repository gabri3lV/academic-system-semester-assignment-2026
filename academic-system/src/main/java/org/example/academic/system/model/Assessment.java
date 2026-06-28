package org.example.academic.system.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public abstract class Assessment {

    @Positive(message = "Assessment value must be positive.")
    private double value;

    @DecimalMin(value = "0.0", inclusive = false,
            message = "Weight must be greater than 0.")
    @DecimalMax(value = "1.0",
            message = "Weight must be at most 1.0.")
    private double weight;
}