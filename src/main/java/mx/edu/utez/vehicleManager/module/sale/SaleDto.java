package mx.edu.utez.vehicleManager.module.sale;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class SaleDto {
    @NotNull(message = "El id del vehículo es obligatorio")
    private Long vehicleId;
    @NotNull(message = "El id del cliente es obligatorio")
    private Long customerId;
    private List<Long> serviceIds;

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Long> serviceIds) {
        this.serviceIds = serviceIds;
    }
}
