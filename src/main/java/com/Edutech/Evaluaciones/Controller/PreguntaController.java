package com.Edutech.Evaluaciones.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Pregunta> crearPregunta(@RequestBody Pregunta pregunta){
        Pregunta nuevPregunta = preguntaService.crearPregunta(pregunta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevPregunta);
    }

    @GetMapping
    @Operation(summary = "Listar preguntas", description = "Esta funcion permite listar todas las instancias de preguntas registradas en la base de datos.")
    public List<Pregunta> listaPreguntas(){
        return preguntaService.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar instancia por ID", description = "Esta funcion permite buscar una instancia especifica de Pregunta a travez del ID de la misma.")
    public ResponseEntity<Pregunta> buscarxid(@PathVariable int id){
        return preguntaService.buscarxid(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar instancia", description = "Esta funcion permite actualizar los datos una instancia de Pregunta ya existente.")
    public ResponseEntity<Pregunta> actualizarPregunta(@PathVariable int id, @RequestBody Pregunta nuevaPregunta){
        return preguntaService.actualizarPregunta(id, nuevaPregunta)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar instancia", description = "Esta funcion permite eliminar una instancia existente de Pregunta, de la base de datos.")
    public ResponseEntity<Void> eliminarPregunta(@PathVariable int id){
        preguntaService.eliminarPregunta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/validar/{respuesta}")
    @Operation(summary = "Validar respuesta de pregunta", description = "Esta funcion permite validar si la respuesta de una pregunta es correcta o no mediante un boolean. Si el parametro de Respuesta coincide con el numero de respuesta correcta de la pregunta, entonces esta devolverá un TRUE.")
    public ResponseEntity<Boolean> validar(@PathVariable int id, @PathVariable int respuesta){
        boolean esCorrecto = preguntaService.validarRespuesta(id, respuesta);
        return ResponseEntity.ok(esCorrecto);
    }
}
