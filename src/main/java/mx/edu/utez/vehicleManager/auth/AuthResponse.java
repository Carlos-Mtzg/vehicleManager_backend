package mx.edu.utez.vehicleManager.auth;

public class AuthResponse {
    private String token;

    // Constructors
    public AuthResponse() {
    }

    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter & Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Builder
    public static class Builder {
        private String token;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(token);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
