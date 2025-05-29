package pe.edu.vallegrande.distribution_microservice.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "programacion_distribucion")
public class ProgramacionDistribucion {

    @Id
    private String id;

    private String calles;
    private String zonas;
    private String horaInicio;
    private String horaFin;
    private Boolean esDiario;
    private String observaciones;
    private String responsableId;
    private String calleNombre;
    private String zonaNombre;
    private Boolean activo;
    private Boolean eliminado;
    private LocalDate fechaRegistro;
}
