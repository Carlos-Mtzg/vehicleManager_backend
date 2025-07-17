package mx.edu.utez.vehicleManager.module.service;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ServiceDto {
    @Pattern(regexp = "^[^<>]*$", message = "No se permiten < o >")
    @Size(max = 50)
    private String name;

    @Pattern(regexp = "^[^<>]*$", message = "No se permiten < o >")
    @Size(max = 150)
    private String description;

    @Positive(message = "El precio debe ser mayor a 0")
    @Digits(integer = 7, fraction = 2)
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
