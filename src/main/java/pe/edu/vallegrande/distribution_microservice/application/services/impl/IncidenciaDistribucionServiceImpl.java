package pe.edu.vallegrande.distribution_microservice.application.services.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import pe.edu.vallegrande.distribution_microservice.application.services.IncidenciaDistribucionService;
import pe.edu.vallegrande.distribution_microservice.domain.models.IncidenciaDistribucion;
import pe.edu.vallegrande.distribution_microservice.infrastructure.repository.IncidenciaDistribucionRepository;

@Service
public class IncidenciaDistribucionServiceImpl implements IncidenciaDistribucionService {

    private final IncidenciaDistribucionRepository repository;

    public IncidenciaDistribucionServiceImpl(IncidenciaDistribucionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<IncidenciaDistribucion> listar() {
        return repository.findAll();
    }

    @Override
    public Flux<IncidenciaDistribucion> listarActivos() {
        return repository.findByActivoTrueAndEliminadoFalse();
    }

    @Override
    public Flux<IncidenciaDistribucion> listarInactivos() {
        return repository.findByActivoFalseAndEliminadoFalse();
    }

    @Override
    public Mono<IncidenciaDistribucion> obtenerPorId(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<IncidenciaDistribucion> registrar(IncidenciaDistribucion incidenciaDistribucion) {
        incidenciaDistribucion.setActivo(true);
        incidenciaDistribucion.setEliminado(false);
        return repository.save(incidenciaDistribucion);
    }

    @Override
    public Mono<IncidenciaDistribucion> actualizar(String id, IncidenciaDistribucion incidenciaDistribucion) {
        return repository.findById(id)
                .flatMap(existing -> {
                    incidenciaDistribucion.setId(existing.getId());
                    incidenciaDistribucion.setActivo(existing.getActivo());
                    incidenciaDistribucion.setEliminado(existing.getEliminado());
                    return repository.save(incidenciaDistribucion);
                });
    }

    @Override
    public Mono<Void> eliminar(String id) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setEliminado(true);
                    existing.setActivo(false);
                    return repository.save(existing);
                })
                .then();
    }

    @Override
    public Mono<Void> eliminarFisico(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<IncidenciaDistribucion> activar(String id) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setActivo(true);
                    existing.setEliminado(false);
                    return repository.save(existing);
                });
    }

    @Override
    public Mono<IncidenciaDistribucion> desactivar(String id) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setActivo(false);
                    return repository.save(existing);
                });
    }
}
