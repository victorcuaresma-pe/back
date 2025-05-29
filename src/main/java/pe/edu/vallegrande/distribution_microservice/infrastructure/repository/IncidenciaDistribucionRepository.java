package pe.edu.vallegrande.distribution_microservice.infrastructure.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import pe.edu.vallegrande.distribution_microservice.domain.models.IncidenciaDistribucion;

public interface IncidenciaDistribucionRepository extends ReactiveMongoRepository<IncidenciaDistribucion, String> {

    Flux<IncidenciaDistribucion> findByActivoTrueAndEliminadoFalse();

    Flux<IncidenciaDistribucion> findByActivoFalseAndEliminadoFalse();
}
