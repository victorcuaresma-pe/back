package pe.edu.vallegrande.distribution_microservice.infrastructure.dto.response.subresponse;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ActualizacionResponse {
    private LocalDateTime fecha;
    private String estado;
    private String descripcion;
    private String usuarioId;
}
