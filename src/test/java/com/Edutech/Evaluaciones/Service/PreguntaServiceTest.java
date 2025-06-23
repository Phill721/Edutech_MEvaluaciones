package com.Edutech.Evaluaciones.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Edutech.Evaluaciones.Model.Pregunta;
import com.Edutech.Evaluaciones.Repository.PreguntaRepository;

public class PreguntaServiceTest {
    @Mock
    private PreguntaRepository preguntaRepository;
    @InjectMocks
    private PreguntaService preguntaService;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearPregunta(){
        Pregunta pregunta = new Pregunta(0, "ASD", null, 1,
         10, "Bien hecho", null);
        Pregunta guardada = new Pregunta(1, "ASD", null, 1,
         10, "Bien hecho", null);
        when(preguntaRepository.save(pregunta)).thenReturn(guardada);
        Pregunta resultado = preguntaService.crearPregunta(pregunta);
        assertThat(resultado.getId()).isEqualTo(1);
        verify(preguntaRepository).save(pregunta);

    }

    @Test
    void testListar(){
        Pregunta p1 = new Pregunta(1, "ASD", null, 1,
         10, "Bien hecho", null);
        Pregunta p2 = new Pregunta(2, "ASDF", null, 1,
         10, "Bien hecho", null);
        when(preguntaRepository.findAll()).thenReturn(Arrays.asList(p1,p2));
        List<Pregunta> resultado = preguntaService.listar();
        assertThat(resultado).hasSize(2).contains(p1,p2);
        verify(preguntaRepository).findAll();
    }

    @Test
    void testEliminarPregunta(){
        int id = 1;
        doNothing().when(preguntaRepository).deleteById(id);
        preguntaService.eliminarPregunta(id);
        verify(preguntaRepository).deleteById(id);
    }
    
    @Test
    void testBuscarxid(){
        int id = 1;
        Pregunta simulado = new Pregunta(id, "ASD", null, 1,
         10, "Bien hecho", null);
        when(preguntaRepository.findById(id)).thenReturn(Optional.of(simulado));
        Optional<Pregunta> resultado = preguntaService.buscarxid(id);
        assertThat(resultado).isPresent().hasValueSatisfying(pregunta -> {
            assertThat(pregunta.getId()).isEqualTo(id);
            assertThat(pregunta.getEnunciado()).isEqualTo("ASD");
            assertThat(pregunta.getOpciones()).isEqualTo(null);
            assertThat(pregunta.getNrorespuestacorrecta()).isEqualTo(1);
            assertThat(pregunta.getPuntos()).isEqualTo(10);
            assertThat(pregunta.getFeedback()).isEqualTo("Bien hecho");
            assertThat(pregunta.getEvaluacion()).isEqualTo(null);
        });
        verify(preguntaRepository).findById(id);
    }

    @Test
    void testActualizarPregunta(){
        int id = 1;
        Pregunta existente = new Pregunta(id, "ASD", null, 1,
         10, "Bien hecho", null);
        Pregunta actualizada = new Pregunta(id, "QWE", null, 2,
         15, "Bien hecho", null);
        when(preguntaRepository.findById(id)).thenReturn(Optional.of(existente));
        when(preguntaRepository.save(existente)).thenReturn(existente);
        Optional<Pregunta> resultado = preguntaService.actualizarPregunta(id, actualizada);
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getEnunciado()).isEqualTo("QWE");
        assertThat(resultado.get().getNrorespuestacorrecta()).isEqualTo(2);
        assertThat(resultado.get().getPuntos()).isEqualTo(15);
        verify(preguntaRepository).findById(id);
        verify(preguntaRepository).save(existente);
    }
    
}
