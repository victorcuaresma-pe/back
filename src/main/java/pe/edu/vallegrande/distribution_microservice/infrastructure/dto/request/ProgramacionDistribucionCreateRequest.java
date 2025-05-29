package pe.edu.vallegrande.distribution_microservice.infrastructure.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import reactor.core.publisher.Flux;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramacionDistribucionCreateRequest {

    
    private String calleId;

    
    private String zonaId;

    
    private String horaInicio;
    
    private String horaFin;

    
    private Boolean esDiario;

    private String observaciones;

    
    private String responsableId;

    
    private String calleNombre;

    
    private String zonaNombre;
}
