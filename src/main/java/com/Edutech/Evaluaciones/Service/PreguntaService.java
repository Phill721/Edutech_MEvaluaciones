package com.Edutech.Evaluaciones.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Edutech.Evaluaciones.Model.Pregunta;
import com.Edutech.Evaluaciones.Repository.PreguntaRepository;

@Service
public class PreguntaService {
    @Autowired
    private PreguntaRepository preguntaRepository;

    public Pregunta crearPregunta(Pregunta pregunta){
        return preguntaRepository.save(pregunta);
    }

    public List<Pregunta> listar(){
        return preguntaRepository.findAll();
    }

    public Optional<Pregunta> buscarxid(int id){
        return preguntaRepository.findById(id);
    }

    public boolean validarRespuesta(int preguntaid, int respuesta){
        return preguntaRepository.findById(preguntaid)
            .map(pregunta -> pregunta.escorrecta(respuesta))
            .orElse(false);
    }

    public Optional<Pregunta> actualizarPregunta(int id, Pregunta updatedPregunta){
        return preguntaRepository.findById(id).map(pregunta -> {
            pregunta.setId(updatedPregunta.getId());
            pregunta.setEnunciado(updatedPregunta.getEnunciado());
            pregunta.setOpciones(updatedPregunta.getOpciones());
            pregunta.setNrorespuestacorrecta(updatedPregunta.getNrorespuestacorrecta());
            pregunta.setPuntos(updatedPregunta.getPuntos());
            pregunta.setFeedback(updatedPregunta.getFeedback());
            pregunta.setEvaluacion(updatedPregunta.getEvaluacion());
            return preguntaRepository.save(pregunta);
        });
    }

    public void eliminarPregunta(int id){
        preguntaRepository.deleteById(id);
    }

}
