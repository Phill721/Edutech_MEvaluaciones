package com.Edutech.Evaluaciones.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel; // ← HATEOAS
import org.springframework.hateoas.CollectionModel; // ← HATEOAS
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*; // ← HATEOAS

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Edutech.Evaluaciones.Model.Evaluacion;
import com.Edutech.Evaluaciones.Service.EvaluacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/evaluaciones")
@Tag(name = "Evaluaciones", description = "Operaciones relacionadas con las instancias de Evaluacion")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @PostMapping
    @Operation(summary = "Crear una evaluacion", description = "Permite crear una instancia de evaluacion y guardarla en la base de datos.")
    public ResponseEntity<EntityModel<Evaluacion>> crearEvaluacion(@RequestBody Evaluacion evaluacion) {
        Evaluacion nueva = evaluacionService.crearEvaluacion(evaluacion);

        EntityModel<Evaluacion> evaluacionModel = EntityModel.of(nueva,
                linkTo(methodOn(EvaluacionController.class).buscarporID(nueva.getId())).withSelfRel(),
                linkTo(methodOn(EvaluacionController.class).listarEvaluaciones()).withRel("evaluaciones"));

        return ResponseEntity.status(HttpStatus.CREATED).body(evaluacionModel);
    }

    @GetMapping
    @Operation(summary = "Listar evaluaciones", description = "Esta funcion permite listar todas las instancias de evaluaciones registradas en la base de datos.")
    public CollectionModel<EntityModel<Evaluacion>> listarEvaluaciones() {
        List<Evaluacion> lista = evaluacionService.listar();

        List<EntityModel<Evaluacion>> evaluacionesConLinks = lista.stream()
                .map(eva -> EntityModel.of(eva,
                        linkTo(methodOn(EvaluacionController.class).buscarporID(eva.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(evaluacionesConLinks,
                linkTo(methodOn(EvaluacionController.class).listarEvaluaciones()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar instancia por ID", description = "Esta funcion permite buscar una instancia especifica de Evaluacion a travez del ID de la misma.")
    public ResponseEntity<EntityModel<Evaluacion>> buscarporID(@PathVariable int id) {
        return evaluacionService.buscarporID(id)
                .map(eva -> EntityModel.of(eva,
                        linkTo(methodOn(EvaluacionController.class).buscarporID(id)).withSelfRel(),
                        linkTo(methodOn(EvaluacionController.class).listarEvaluaciones()).withRel("evaluaciones")))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar instancia", description = "Esta funcion permite actualizar los datos una instancia de Evaluacion ya existente.")
    public ResponseEntity<EntityModel<Evaluacion>> actualizarEvaluacion(@PathVariable int id, @RequestBody Evaluacion nuevaEvaluacion) {
        return evaluacionService.actualizarEvaluacion(id, nuevaEvaluacion)
                .map(eva -> EntityModel.of(eva,
                        linkTo(methodOn(EvaluacionController.class).buscarporID(id)).withSelfRel(),
                        linkTo(methodOn(EvaluacionController.class).listarEvaluaciones()).withRel("evaluaciones")))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar instancia", description = "Esta funcion permite eliminar una instancia existente de Evaluacion, de la base de datos.")
    public ResponseEntity<Void> eliminarEvaluacion(@PathVariable int id) {
        evaluacionService.eliminarEvaluacion(id);
        return ResponseEntity.noContent().build();
    }
}