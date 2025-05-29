package pe.edu.vallegrande.distribution_microservice.application.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.distribution_microservice.domain.models.IncidenciaDistribucion;

public interface IncidenciaDistribucionService {

    Flux<IncidenciaDistribucion> listar();

    Flux<IncidenciaDistribucion> listarActivos();

    Flux<IncidenciaDistribucion> listarInactivos();

    Mono<IncidenciaDistribucion> obtenerPorId(String id);

    Mono<IncidenciaDistribucion> registrar(IncidenciaDistribucion incidenciaDistribucion);

    Mono<IncidenciaDistribucion> actualizar(String id, IncidenciaDistribucion incidenciaDistribucion);

    Mono<Void> eliminar(String id);  // eliminación lógica

    Mono<Void> eliminarFisico(String id);  // eliminación física

    Mono<IncidenciaDistribucion> activar(String id);

    Mono<IncidenciaDistribucion> desactivar(String id);
}
