package pe.edu.vallegrande.distribution_microservice.application.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.distribution_microservice.domain.models.ProgramacionDistribucion;
import pe.edu.vallegrande.distribution_microservice.application.services.ProgramacionDistribucionService;
import pe.edu.vallegrande.distribution_microservice.infrastructure.dto.request.ProgramacionDistribucionUpdateRequest; // <- CORRECTO
import pe.edu.vallegrande.distribution_microservice.infrastructure.repository.ProgramacionDistribucionRepository;

@Service
@RequiredArgsConstructor
public class ProgramacionDistribucionServiceImpl implements ProgramacionDistribucionService {

    private final ProgramacionDistribucionRepository repository;

    @Override
    public Flux<ProgramacionDistribucion> listar() {
        return repository.findAll();
    }

    @Override
    public Flux<ProgramacionDistribucion> listarActivos() {
        return repository.findByActivoTrueAndEliminadoFalse();
    }

    @Override
    public Flux<ProgramacionDistribucion> listarInactivos() {
        return repository.findByActivoFalseAndEliminadoFalse();
    }

    @Override
    public Mono<ProgramacionDistribucion> obtenerPorId(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<ProgramacionDistribucion> registrar(ProgramacionDistribucion entidad) {
        // Por ejemplo: siempre crear como activo y no eliminado
        entidad.setActivo(true);
        entidad.setEliminado(false);
        return repository.save(entidad);
    }

    @Override
    public Mono<ProgramacionDistribucion> actualizar(String id, ProgramacionDistribucionUpdateRequest dto) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setCalles(dto.getCalleId());
                    existing.setZonas(dto.getZonaId());
                    existing.setHoraInicio(dto.getHoraInicio());
                    existing.setHoraFin(dto.getHoraFin());
                    existing.setEsDiario(dto.getEsDiario());
                    existing.setObservaciones(dto.getObservaciones());
                    existing.setResponsableId(dto.getResponsableId());
                    existing.setCalleNombre(dto.getCalleNombre());
                    existing.setZonaNombre(dto.getZonaNombre());
                    return repository.save(existing);
                });
    }

    @Override
    public Mono<Void> eliminar(String id) {
        // Eliminación lógica: setear eliminado = true
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setEliminado(true);
                    return repository.save(existing);
                })
                .then();
    }

    @Override
    public Mono<Void> eliminarFisico(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<ProgramacionDistribucion> activar(String id) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setActivo(true);
                    return repository.save(existing);
                });
    }

    @Override
    public Mono<ProgramacionDistribucion> desactivar(String id) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setActivo(false);
                    return repository.save(existing);
                });
    }
    
}
