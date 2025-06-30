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
    public ResponseEntity<Evaluacion> crearEvaluacion(@RequestBody Evaluacion evaluacion){
        Evaluacion newEvaluacion = evaluacionService.crearEvaluacion(evaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEvaluacion);
    }

    @GetMapping
    @Operation(summary = "Listar evaluaciones", description = "Esta funcion permite listar todas las instancias de evaluaciones registradas en la base de datos.")
    public List<Evaluacion> listarEvaluaciones(){
        return evaluacionService.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar instancia por ID", description = "Esta funcion permite buscar una instancia especifica de Evaluacion a travez del ID de la misma.")
    public ResponseEntity<Evaluacion> buscarporID(@PathVariable int id){
        return evaluacionService.buscarporID(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar instancia", description = "Esta funcion permite actualizar los datos una instancia de Evaluacion ya existente.")
    public ResponseEntity<Evaluacion> actualizarEvaluacion(@PathVariable int id, @RequestBody Evaluacion nuevaEvaluacion){
        return evaluacionService.actualizarEvaluacion(id, nuevaEvaluacion)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar instancia", description = "Esta funcion permite eliminar una instancia existente de Evaluacion, de la base de datos.")
    public ResponseEntity<Void> eliminarEvaluacion(@PathVariable int id){
        evaluacionService.eliminarEvaluacion(id);
        return ResponseEntity.noContent().build();
    }
}
