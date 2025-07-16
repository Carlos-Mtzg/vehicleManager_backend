package mx.edu.utez.vehicleManager.module.service;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import mx.edu.utez.vehicleManager.module.vehicle.VehicleModel;

@Entity
@Table(name = "service")
public class ServiceModel {

    public static final String NOT_BLANK_MESSAGE = "Este campo no puede estar vacío";
    public static final String NO_ANGLE_BRACKETS_MESSAGE = "No se permiten los caracteres < o >";
    public static final String NOT_NULL_MESSAGE = "Este campo es obligatorio";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "services")
    @JsonIgnore
    private List<VehicleModel> vehicles;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Pattern(regexp = "^[^<>]*$", message = NO_ANGLE_BRACKETS_MESSAGE)
    @Pattern(regexp = "^[A-Za-z]{4}-\\d{2}$", message = "El código debe tener el formato: 4 letras, guión y 2 números (ejemplo: LLAN-01)")
    @Size(max = 7, message = "Este campo no puede tener más de 7 carácteres")
    private String code;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Pattern(regexp = "^[^<>]*$", message = NO_ANGLE_BRACKETS_MESSAGE)
    @Size(max = 50, message = "Este campo no puede tener más de 50 caracteres")
    private String name;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Pattern(regexp = "^[^<>]*$", message = NO_ANGLE_BRACKETS_MESSAGE)
    @Size(max = 150, message = "Este campo no puede tener más de 150 caracteres")
    private String description;

    @NotNull(message = NOT_NULL_MESSAGE)
    @Positive(message = "El precio debe ser mayor a 0")
    @Digits(integer = 7, fraction = 2, message = "El precio debe tener máximo 7 dígitos enteros y 2 decimales")
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<VehicleModel> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleModel> vehicles) {
        this.vehicles = vehicles;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
