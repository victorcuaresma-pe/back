package pe.edu.vallegrande.distribution_microservice.infrastructure.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramacionDistribucionUpdateRequest {

    private String id;

    private String calleId;
    private String zonaId;
    private String horaInicio;
    private String horaFin;
    private Boolean esDiario;
    private String observaciones;
    private String responsableId;
    private String calleNombre;
    private String zonaNombre;
    private Boolean activo;
    private Boolean eliminado;
}
