package pe.edu.vallegrande.distribution_microservice.infrastructure.dto.request.subrequest;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CalleAfectadaRequest {
    @NotBlank
    private String calleId;
    @NotBlank
    private String calleNombre;
}
