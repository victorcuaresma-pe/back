package pe.edu.vallegrande.distribution_microservice.application.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.distribution_microservice.domain.models.ProgramacionDistribucion;
import pe.edu.vallegrande.distribution_microservice.infrastructure.dto.request.ProgramacionDistribucionUpdateRequest;

public interface ProgramacionDistribucionService {

    Flux<ProgramacionDistribucion> listar();

    Flux<ProgramacionDistribucion> listarActivos();

    Flux<ProgramacionDistribucion> listarInactivos();

    Mono<ProgramacionDistribucion> obtenerPorId(String id);

    Mono<ProgramacionDistribucion> registrar(ProgramacionDistribucion entidad);

    Mono<ProgramacionDistribucion> actualizar(String id, ProgramacionDistribucionUpdateRequest dto); // <- ACTUALIZADO

    Mono<Void> eliminar(String id);

    Mono<Void> eliminarFisico(String id);

    Mono<ProgramacionDistribucion> activar(String id);

    Mono<ProgramacionDistribucion> desactivar(String id);
}
