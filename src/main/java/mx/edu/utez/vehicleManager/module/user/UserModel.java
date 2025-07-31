package mx.edu.utez.vehicleManager.module.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import mx.edu.utez.vehicleManager.module.employee.EmployeeModel;
import mx.edu.utez.vehicleManager.module.role.RoleModel;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class UserModel implements UserDetails {

    public static final String NOT_BLANK_MESSAGE = "Este campo no puede estar vacío";
    public static final String NO_ANGLE_BRACKETS_MESSAGE = "No se permiten los caracteres < o >";
    public static final String NOT_NULL_MESSAGE = "Este campo es obligatorio";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = NOT_NULL_MESSAGE)
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Pattern(regexp = "^[^<>]*$", message = NO_ANGLE_BRACKETS_MESSAGE)
    @Size(max = 50, message = "Este campo no puede tener más de 50 caracteres")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Pattern(regexp = "^[^<>]*$", message = NO_ANGLE_BRACKETS_MESSAGE)
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @Column(nullable = true)
    private boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleModel role;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private EmployeeModel employee;

    // Constructors
    public UserModel() {
    }

    public UserModel(Long id, String username, String password, RoleModel role, EmployeeModel employee) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.employee = employee;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username != null ? username.trim() : null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password != null ? password.trim() : null;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.getName())));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // Builder
    public static class Builder {
        private Long id;
        private String username;
        private String password;
        private RoleModel role;
        private EmployeeModel employee;
        private boolean enabled = true;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(RoleModel role) {
            this.role = role;
            return this;
        }

        public Builder employee(EmployeeModel employee) {
            this.employee = employee;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserModel build() {
            UserModel user = new UserModel();
            user.setId(id);
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);
            user.setEmployee(employee);
            user.enabled = enabled;
            return user;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
