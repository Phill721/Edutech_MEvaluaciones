package com.Edutech.Evaluaciones.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Edutech.Evaluaciones.Model.Evaluacion;
import com.Edutech.Evaluaciones.Repository.EvaluacionRepository;

@Service
public class EvaluacionService {
    @Autowired
    private EvaluacionRepository evaluacionRepository;

    public Evaluacion crearEvaluacion(Evaluacion evaluacion){
        return evaluacionRepository.save(evaluacion);
    }

    public Optional<Evaluacion> buscarporID(int id){
        return evaluacionRepository.findById(id);
    }

    public Optional<Evaluacion> actualizarEvaluacion(int id, Evaluacion updatedEvaluacion){
        return evaluacionRepository.findById(id).map(evaluacion -> {
            evaluacion.setId(updatedEvaluacion.getId());
            evaluacion.setTitulo(updatedEvaluacion.getTitulo());
            evaluacion.setFecha(updatedEvaluacion.getFecha());
            evaluacion.setHorainicio(updatedEvaluacion.getHorainicio());
            evaluacion.setHorafin(updatedEvaluacion.getHorafin());
            evaluacion.setPuntajetotal(updatedEvaluacion.getPuntajetotal());
            evaluacion.setTipo(updatedEvaluacion.getTipo());
            evaluacion.setEstado(updatedEvaluacion.getEstado());
            evaluacion.setInstrucciones(updatedEvaluacion.getInstrucciones());
            evaluacion.setHabilidadesevaluadas(updatedEvaluacion.getHabilidadesevaluadas());
            return evaluacionRepository.save(evaluacion);
        });
    }

    public boolean eliminarEvaluacion(int id){
        if(evaluacionRepository.existsById(id)){
            evaluacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
