package pe.edu.vallegrande.distribution_microservice.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.distribution_microservice.application.services.IncidenciaDistribucionService;
import pe.edu.vallegrande.distribution_microservice.domain.models.IncidenciaDistribucion;
import pe.edu.vallegrande.distribution_microservice.infrastructure.dto.ErrorMessage;
import pe.edu.vallegrande.distribution_microservice.infrastructure.dto.ResponseDto;


import java.util.List;

@RestController
@RequestMapping("/api/v1/incidencias")
@CrossOrigin("*")
@RequiredArgsConstructor
public class IncidenciaDistribucionRest {

    private final IncidenciaDistribucionService service;

    // Obtener todas las incidencias (activas e inactivas)
    @GetMapping
    public Mono<ResponseDto<List<IncidenciaDistribucion>>> getAll() {
        return service.listar()
                .collectList()
                .map(list -> new ResponseDto<>(true, list))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al listar", e.getMessage()))));
    }

    // Obtener todas las incidencias activas
    @GetMapping("/active")
    public Mono<ResponseDto<List<IncidenciaDistribucion>>> getAllActive() {
        return service.listarActivos()
                .collectList()
                .map(list -> new ResponseDto<>(true, list))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al listar activos", e.getMessage()))));
    }

    // Obtener todas las incidencias inactivas
    @GetMapping("/inactive")
    public Mono<ResponseDto<List<IncidenciaDistribucion>>> getAllInactive() {
        return service.listarInactivos()
                .collectList()
                .map(list -> new ResponseDto<>(true, list))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al listar inactivos", e.getMessage()))));
    }

    // Obtener una incidencia por ID
    @GetMapping("/{id}")
    public Mono<ResponseDto<IncidenciaDistribucion>> getById(@PathVariable String id) {
        return service.obtenerPorId(id)
                .map(data -> new ResponseDto<>(true, data))
                .switchIfEmpty(Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.NOT_FOUND.value(), "No encontrado", "No se encontró ID: " + id))))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al buscar", e.getMessage()))));
    }

    // Crear una nueva incidencia
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseDto<IncidenciaDistribucion>> create(@RequestBody IncidenciaDistribucion request) {
        return service.registrar(request)
                .map(saved -> new ResponseDto<>(true, saved))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Error al crear", e.getMessage()))));
    }

    // Actualizar una incidencia existente
    @PutMapping("/{id}")
    public Mono<ResponseDto<IncidenciaDistribucion>> update(@PathVariable String id, @RequestBody IncidenciaDistribucion request) {
        return service.actualizar(id, request)
                .map(updated -> new ResponseDto<>(true, updated))
                .switchIfEmpty(Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.NOT_FOUND.value(), "No encontrado", "No se encontró ID: " + id))))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Error al actualizar", e.getMessage()))));
    }

    // Eliminación lógica de una incidencia (marca como inactiva sin borrarla)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseDto<Object>> deleteLogic(@PathVariable String id) {
        return service.eliminar(id)
                .then(Mono.just(new ResponseDto<>(true, null)))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Error al eliminar", e.getMessage()))));
    }

    // Eliminación física de una incidencia (elimina completamente de la base de datos)
    @DeleteMapping("/{id}/permanent")
    public Mono<ResponseDto<Object>> deletePermanent(@PathVariable String id) {
        return service.eliminarFisico(id)
                .then(Mono.just(new ResponseDto<>(true, null)))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Error al eliminar físicamente", e.getMessage()))));
    }

    // Activar una incidencia (cambiar estado a activo)
    @PatchMapping("/{id}/activate")
    public Mono<ResponseDto<IncidenciaDistribucion>> activate(@PathVariable String id) {
        return service.activar(id)
                .map(data -> new ResponseDto<>(true, data))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Error al activar", e.getMessage()))));
    }

    // Desactivar una incidencia (cambiar estado a inactivo)
    @PatchMapping("/{id}/deactivate")
    public Mono<ResponseDto<IncidenciaDistribucion>> deactivate(@PathVariable String id) {
        return service.desactivar(id)
                .map(data -> new ResponseDto<>(true, data))
                .onErrorResume(e -> Mono.just(new ResponseDto<>(false,
                        new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Error al desactivar", e.getMessage()))));
    }
}
