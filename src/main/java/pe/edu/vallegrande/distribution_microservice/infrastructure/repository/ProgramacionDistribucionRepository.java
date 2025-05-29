package pe.edu.vallegrande.distribution_microservice.infrastructure.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import pe.edu.vallegrande.distribution_microservice.domain.models.ProgramacionDistribucion;


public interface ProgramacionDistribucionRepository extends ReactiveMongoRepository<ProgramacionDistribucion, String> {

    Flux<ProgramacionDistribucion> findByActivoTrueAndEliminadoFalse();

    Flux<ProgramacionDistribucion> findByActivoFalseAndEliminadoFalse();
}
