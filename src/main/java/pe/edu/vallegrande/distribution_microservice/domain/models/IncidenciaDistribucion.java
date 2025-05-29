package pe.edu.vallegrande.distribution_microservice.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

import pe.edu.vallegrande.distribution_microservice.infrastructure.dto.request.subrequest.ActualizacionRequest;
import pe.edu.vallegrande.distribution_microservice.infrastructure.dto.request.subrequest.CalleAfectadaRequest;
import pe.edu.vallegrande.distribution_microservice.infrastructure.dto.request.subrequest.MaterialRequeridoRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "incidencias_distribucion")
public class IncidenciaDistribucion {

    @Id
    private String id;

    private String tipoIncidencia;
    private String descripcion;
    private String zonaId;
    private String zonaNombre;
    private List<CalleAfectadaRequest> callesAfectadas;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaEstimadaSolucion;
    private LocalDateTime fechaSolucionReal;
    private String estado;
    private String prioridad;
    private String reportadoPor;
    private String asignadoA;
    private List<MaterialRequeridoRequest> materialesRequeridos;
    private List<ActualizacionRequest> actualizaciones;
    private String observaciones;
    private Boolean notificado;
    private Boolean activo;
    private Boolean eliminado;
    private LocalDateTime fechaRegistro;
}
