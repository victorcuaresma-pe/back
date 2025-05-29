package pe.edu.vallegrande.distribution_microservice.infrastructure.dto.request;

import pe.edu.vallegrande.distribution_microservice.infrastructure.dto.request.subrequest.ActualizacionRequest;
import pe.edu.vallegrande.distribution_microservice.infrastructure.dto.request.subrequest.CalleAfectadaRequest;
import pe.edu.vallegrande.distribution_microservice.infrastructure.dto.request.subrequest.MaterialRequeridoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidenciaDistribucionCreateRequest {

    
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
}
