package mx.edu.utez.vehicleManager.module.employee;

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
import mx.edu.utez.vehicleManager.module.customer.CustomerModel;
import mx.edu.utez.vehicleManager.module.role.RoleModel;

@Entity
@Table(name = "employee")
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String full_name;
    private String phone;
    private String email;


    //Relations
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleModel role;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<CustomerModel> customers;

    public Long getId() {
        return id;
    }


     //Getters and Setters
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

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

}
