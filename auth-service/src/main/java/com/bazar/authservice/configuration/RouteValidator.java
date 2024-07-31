package com.bazar.authservice.configuration;

import com.bazar.authservice.dto.RequestDTO;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
@ConfigurationProperties(prefix = "admin-paths")
@Data
public class RouteValidator {
    private List<RequestDTO> paths;

    public boolean isAdminPath(RequestDTO requestDTO) {
        return paths.stream().anyMatch(p ->
                Pattern.matches(p.getUri(),requestDTO.getUri()) && p.getMethod().equals(requestDTO.getMethod()));
    }
}
