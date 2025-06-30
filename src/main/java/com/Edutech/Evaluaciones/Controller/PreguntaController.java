package com.Edutech.Evaluaciones.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Edutech.Evaluaciones.Model.Pregunta;
import com.Edutech.Evaluaciones.Service.PreguntaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/preguntas")
@Tag(name = "Preguntas", description = "Operaciones relacionadas con las instancias de Pregunta")
public class PreguntaController {

    @Autowired
    private PreguntaService preguntaService;

    @PostMapping
    @Operation(summary = "Crear una pregunta", description = "Permite crear una instancia de Pregunta y guardarla en la base de datos.")
    public ResponseEntity<EntityModel<Pregunta>> crearPregunta(@RequestBody Pregunta pregunta) {
        Pregunta nueva = preguntaService.crearPregunta(pregunta);

        EntityModel<Pregunta> preguntaModel = EntityModel.of(nueva,
                linkTo(methodOn(PreguntaController.class).buscarxid(nueva.getId())).withSelfRel(),
                linkTo(methodOn(PreguntaController.class).listaPreguntas()).withRel("preguntas"));

        return ResponseEntity.status(HttpStatus.CREATED).body(preguntaModel);
    }

    @GetMapping
    @Operation(summary = "Listar preguntas", description = "Esta funcion permite listar todas las instancias de preguntas registradas en la base de datos.")
    public CollectionModel<EntityModel<Pregunta>> listaPreguntas() {
        List<Pregunta> lista = preguntaService.listar();

        List<EntityModel<Pregunta>> preguntasConLinks = lista.stream()
                .map(preg -> EntityModel.of(preg,
                        linkTo(methodOn(PreguntaController.class).buscarxid(preg.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(preguntasConLinks,
                linkTo(methodOn(PreguntaController.class).listaPreguntas()).withSelfRel());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar instancia por ID", description = "Esta funcion permite buscar una instancia especifica de Pregunta a travez del ID de la misma.")
    public ResponseEntity<EntityModel<Pregunta>> buscarxid(@PathVariable int id) {
        return preguntaService.buscarxid(id)
                .map(preg -> EntityModel.of(preg,
                        linkTo(methodOn(PreguntaController.class).buscarxid(id)).withSelfRel(),
                        linkTo(methodOn(PreguntaController.class).listaPreguntas()).withRel("preguntas")))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar instancia", description = "Esta funcion permite actualizar los datos una instancia de Pregunta ya existente.")
    public ResponseEntity<EntityModel<Pregunta>> actualizarPregunta(@PathVariable int id, @RequestBody Pregunta nuevaPregunta) {
        return preguntaService.actualizarPregunta(id, nuevaPregunta)
                .map(preg -> EntityModel.of(preg,
                        linkTo(methodOn(PreguntaController.class).buscarxid(id)).withSelfRel(),
                        linkTo(methodOn(PreguntaController.class).listaPreguntas()).withRel("preguntas")))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar instancia", description = "Esta funcion permite eliminar una instancia existente de Pregunta, de la base de datos.")
    public ResponseEntity<Void> eliminarPregunta(@PathVariable int id) {
        preguntaService.eliminarPregunta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/validar/{respuesta}")
    @Operation(summary = "Validar respuesta de pregunta", description = "Permite validar si la respuesta ingresada es correcta.")
    public ResponseEntity<EntityModel<Boolean>> validar(@PathVariable int id, @PathVariable int respuesta) {
        boolean esCorrecto = preguntaService.validarRespuesta(id, respuesta);

        EntityModel<Boolean> respuestaModel = EntityModel.of(esCorrecto,
                linkTo(methodOn(PreguntaController.class).buscarxid(id)).withRel("pregunta"),
                linkTo(methodOn(PreguntaController.class).validar(id, respuesta)).withSelfRel());

        return ResponseEntity.ok(respuestaModel);
    }
}