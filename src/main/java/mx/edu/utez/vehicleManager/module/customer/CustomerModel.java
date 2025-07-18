package mx.edu.utez.vehicleManager.module.customer;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import mx.edu.utez.vehicleManager.module.employee.EmployeeModel;
import mx.edu.utez.vehicleManager.module.vehicle.VehicleModel;

@Entity
@Table(name = "customer")
public class CustomerModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String full_name;
	private String phone;
	private String email;

	@OneToMany(mappedBy = "customer")
	@JsonIgnore
	private List<VehicleModel> vehicles;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private EmployeeModel employee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<VehicleModel> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<VehicleModel> vehicles) {
		this.vehicles = vehicles;
	}

}
