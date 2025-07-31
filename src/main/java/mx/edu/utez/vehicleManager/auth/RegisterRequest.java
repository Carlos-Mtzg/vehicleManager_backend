package mx.edu.utez.vehicleManager.auth;

import mx.edu.utez.vehicleManager.module.employee.EmployeeModel;
import mx.edu.utez.vehicleManager.module.role.RoleModel;

public class RegisterRequest {
    private String username;
    private String password;
    private boolean enabled = true;
    private EmployeeModel employee;
    private RoleModel role;

    // Constructors
    public RegisterRequest() {}

    public RegisterRequest(String username, String password, boolean enabled, EmployeeModel employee, RoleModel role) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.employee = employee;
        this.role = role;
    }

    // Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    // Builder
    public static class Builder {
        private String username;
        private String password;
        private boolean enabled = true;
        private EmployeeModel employee;
        private RoleModel role;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder employee(EmployeeModel employee) {
            this.employee = employee;
            return this;
        }

        public Builder role(RoleModel role) {
            this.role = role;
            return this;
        }

        public RegisterRequest build() {
            return new RegisterRequest(username, password, enabled, employee, role);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
