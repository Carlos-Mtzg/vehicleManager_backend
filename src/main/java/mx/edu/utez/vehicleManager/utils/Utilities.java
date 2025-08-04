package mx.edu.utez.vehicleManager.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Utilities {
    private Utilities() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static ResponseEntity<Object> generateResponse(HttpStatus status, String message, Object data) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("date", new Date());
            map.put("status", status.value());
            map.put("message", message);
            map.put("data", data);
            return new ResponseEntity<>(map, status);
        } catch (Exception e) {
            map.clear();
            map.put("date", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("message", e.getMessage());
            map.put("data", null);
            return new ResponseEntity<>(map, status);
        }
    }

    public static ResponseEntity<Object> authResponse(HttpStatus status, String message, Object data) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("date", new Date());
            map.put("status", status.value());
            map.put("message", message);
            map.put("token", data);
            return new ResponseEntity<>(map, status);
        } catch (Exception e) {
            map.clear();
            map.put("date", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("message", e.getMessage());
            map.put("token", null);
            return new ResponseEntity<>(map, status);
        }
    }
}
