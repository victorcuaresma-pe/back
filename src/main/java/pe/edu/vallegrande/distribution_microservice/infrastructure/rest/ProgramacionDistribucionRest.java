package pe.edu.vallegrande.distribution_microservice.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.distribution_microservice.application.services.ProgramacionDistribucionService;
import pe.edu.vallegrande.distribution_microservice.domain.models.ProgramacionDistribucion;
import pe.edu.vallegrande.distribution_microservice.infrastructure.dto.ErrorMessage;
import pe.edu.vallegrande.distribution_microservice.infrastructure.dto.ResponseDto;
import pe.edu.vallegrande.distribution_microservice.infrastructure.dto.request.ProgramacionDistribucionUpdateRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/programaciones")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ProgramacionDistribucionRest {

    private final ProgramacionDistribucionService service;

    // ✅ Obtener todas las programaciones (activas e inactivas)
    @GetMapping
    public Mono<ResponseDto<List<ProgramacionDistribucion>>> getAll() {
        return service.listar()
                .collectList()
                .map(list -> new ResponseDto<>(true, list))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al listar", e.getMessage()))));
    }

    // ✅ Obtener solo las programaciones activas
    @GetMapping("/active")
    public Mono<ResponseDto<List<ProgramacionDistribucion>>> getAllActive() {
        return service.listarActivos()
                .collectList()
                .map(list -> new ResponseDto<>(true, list))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al listar activos", e.getMessage()))));
    }

    // ✅ Obtener solo las programaciones inactivas
    @GetMapping("/inactive")
    public Mono<ResponseDto<List<ProgramacionDistribucion>>> getAllInactive() {
        return service.listarInactivos()
                .collectList()
                .map(list -> new ResponseDto<>(true, list))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al listar inactivos", e.getMessage()))));
    }

    // ✅ Obtener una programación por su ID
    @GetMapping("/{id}")
    public Mono<ResponseDto<ProgramacionDistribucion>> getById(@PathVariable String id) {
        return service.obtenerPorId(id)
                .map(data -> new ResponseDto<>(true, data))
                .switchIfEmpty(Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.NOT_FOUND.value(), "No encontrado", "No se encontró ID: " + id))))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al buscar", e.getMessage()))));
    }

    // ✅ Registrar una nueva programación de distribución
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseDto<ProgramacionDistribucion>> create(@RequestBody ProgramacionDistribucion request) {
        return service.registrar(request)
                .map(saved -> new ResponseDto<>(true, saved))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Error al crear", e.getMessage()))));
    }

    // ✅ Actualizar una programación existente por su ID
    @PutMapping("/{id}")
    public Mono<ResponseDto<ProgramacionDistribucion>> update(@PathVariable String id,
            @RequestBody ProgramacionDistribucionUpdateRequest request) {
        return service.actualizar(id, request)
                .map(updated -> new ResponseDto<>(true, updated))
                .switchIfEmpty(Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.NOT_FOUND.value(), "No encontrado", "No se encontró ID: " + id))))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Error al actualizar", e.getMessage()))));
    }

    // ✅ Eliminar lógicamente una programación (marca como eliminada, pero no borra del sistema)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseDto<Object>> deleteLogic(@PathVariable String id) {
        return service.eliminar(id)
                .then(Mono.just(new ResponseDto<>(true, null)))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Error al eliminar", e.getMessage()))));
    }

    // ✅ Eliminar físicamente una programación (borra permanentemente de la base de datos)
    @DeleteMapping("/{id}/permanent")
    public Mono<ResponseDto<Object>> deletePermanent(@PathVariable String id) {
        return service.eliminarFisico(id)
                .then(Mono.just(new ResponseDto<>(true, null)))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Error al eliminar físicamente", e.getMessage()))));
    }

    // ✅ Activar una programación (cambia el estado a activo)
    @PatchMapping("/{id}/activate")
    public Mono<ResponseDto<ProgramacionDistribucion>> activate(@PathVariable String id) {
        return service.activar(id)
                .map(data -> new ResponseDto<>(true, data))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Error al activar", e.getMessage()))));
    }

    // ✅ Desactivar una programación (cambia el estado a inactivo)
    @PatchMapping("/{id}/deactivate")
    public Mono<ResponseDto<ProgramacionDistribucion>> deactivate(@PathVariable String id) {
        return service.desactivar(id)
                .map(data -> new ResponseDto<>(true, data))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Error al desactivar", e.getMessage()))));
    }
}
