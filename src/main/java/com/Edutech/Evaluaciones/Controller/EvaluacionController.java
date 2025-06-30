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

@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {
    @Autowired
    private EvaluacionService evaluacionService;

    @PostMapping
    public ResponseEntity<Evaluacion> crearEvaluacion(@RequestBody Evaluacion evaluacion){
        Evaluacion newEvaluacion = evaluacionService.crearEvaluacion(evaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEvaluacion);
    }

    @GetMapping
    public List<Evaluacion> listarEvaluaciones(){
        return evaluacionService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluacion> buscarporID(@PathVariable int id){
        return evaluacionService.buscarporID(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evaluacion> actualizarEvaluacion(@PathVariable int id, @RequestBody Evaluacion nuevaEvaluacion){
        return evaluacionService.actualizarEvaluacion(id, nuevaEvaluacion)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvaluacion(@PathVariable int id){
        evaluacionService.eliminarEvaluacion(id);
        return ResponseEntity.noContent().build();
    }
}
