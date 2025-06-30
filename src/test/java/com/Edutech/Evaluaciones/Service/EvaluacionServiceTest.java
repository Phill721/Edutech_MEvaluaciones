package com.Edutech.Evaluaciones.Service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Edutech.Evaluaciones.Model.EstadoEvaluacion;
import com.Edutech.Evaluaciones.Model.Evaluacion;
import com.Edutech.Evaluaciones.Repository.EvaluacionRepository;

public class EvaluacionServiceTest {
    @Mock
    private EvaluacionRepository evaluacionRepository;
    @InjectMocks
    private EvaluacionService evaluacionService;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearEvaluacion(){
        Evaluacion evaluacion = new Evaluacion(0, "Examen 1", LocalDate.of(2025, 4, 30), 
        LocalDateTime.of(2025, 4, 30, 18, 0, 0), LocalDateTime.of(2025, 4, 30, 19, 0, 0), 
        100, "Seleccion multiple", EstadoEvaluacion.ACTIVO, null, null, "1- XD",
         "No se", null);
        Evaluacion guardado = new Evaluacion(1, "Examen 1", LocalDate.of(2025, 4, 30), 
        LocalDateTime.of(2025, 4, 30, 18, 0, 0), LocalDateTime.of(2025, 4, 30, 19, 0, 0), 
        100, "Seleccion multiple", EstadoEvaluacion.ACTIVO, null, null, "1- XD",
         "No se", null);
        when(evaluacionRepository.save(evaluacion)).thenReturn(guardado);
        Evaluacion resultado = evaluacionService.crearEvaluacion(evaluacion);
        assertThat(resultado.getId()).isEqualTo(1);
        verify(evaluacionRepository).save(evaluacion);
    }

    @Test
    void testListar(){
        Evaluacion e1 = new Evaluacion(1, "Examen 1", LocalDate.of(2025, 4, 30), 
        LocalDateTime.of(2025, 4, 30, 18, 0, 0), LocalDateTime.of(2025, 4, 30, 19, 0, 0), 
        100, "Seleccion multiple", EstadoEvaluacion.ACTIVO, null, null, "1- XD",
         "No se", null);
        Evaluacion e2 = new Evaluacion(2, "Examen 2", LocalDate.of(2025, 4, 30), 
        LocalDateTime.of(2025, 4, 30, 18, 0, 0), LocalDateTime.of(2025, 4, 30, 19, 0, 0), 
        100, "Seleccion multiple", EstadoEvaluacion.ACTIVO, null, null, "1- XD",
         "No se", null);
        
        when(evaluacionRepository.findAll()).thenReturn(Arrays.asList(e1,e2));
        List<Evaluacion> resultado = evaluacionService.listar();
        assertThat(resultado).hasSize(2).contains(e1,e2);
        verify(evaluacionRepository).findAll();
    }

    @Test
    void testEliminarEvaluacion(){
        int id = 1;
        doNothing().when(evaluacionRepository).deleteById(id);
        evaluacionService.eliminarEvaluacion(id);
        verify(evaluacionRepository).deleteById(id);
    }

    @Test
    void testBuscarxid(){
        int id = 1;
        Evaluacion simulada = new Evaluacion(1, "Examen 1", LocalDate.of(2025, 4, 30), 
        LocalDateTime.of(2025, 4, 30, 18, 0, 0), LocalDateTime.of(2025, 4, 30, 19, 0, 0), 
        100, "Seleccion multiple", EstadoEvaluacion.ACTIVO, null, null, "1- XD",
         "No se", null);
        when(evaluacionRepository.findById(id)).thenReturn(Optional.of(simulada));
        Optional<Evaluacion> resultado = evaluacionService.buscarporID(id);
        assertThat(resultado).isPresent().hasValueSatisfying(evaluacion -> {
            assertThat(evaluacion.getId()).isEqualTo(id);
            assertThat(evaluacion.getTitulo()).isEqualTo("Examen 1");
            assertThat(evaluacion.getFecha()).isEqualTo(simulada.getFecha());
            assertThat(evaluacion.getHorainicio()).isEqualTo(simulada.getHorainicio());
            assertThat(evaluacion.getHorafin()).isEqualTo(simulada.getHorafin());
            assertThat(evaluacion.getPuntajetotal()).isEqualTo(100);
            assertThat(evaluacion.getTipo()).isEqualTo("Seleccion multiple");
            assertThat(evaluacion.getEstado()).isEqualTo(EstadoEvaluacion.ACTIVO);
            assertThat(evaluacion.getPreguntas()).isEqualTo(null);
            assertThat(evaluacion.getCalificaciones()).isEqualTo(null);
            assertThat(evaluacion.getInstrucciones()).isEqualTo("1- XD");
            assertThat(evaluacion.getHabilidadesevaluadas()).isEqualTo("No se");
            assertThat(evaluacion.getModulo()).isEqualTo(null);
        });
        verify(evaluacionRepository).findById(id);
    }

    @Test
    void testActualizarEvaluacion(){
        int id = 1;
        Evaluacion existente = new Evaluacion(id, "Examen 1", LocalDate.of(2025, 4, 30), 
        LocalDateTime.of(2025, 4, 30, 18, 0, 0), LocalDateTime.of(2025, 4, 30, 19, 0, 0), 
        100, "Seleccion multiple", EstadoEvaluacion.ACTIVO, null, null, "1- XD",
         "No se", null);
        Evaluacion actualizada = new Evaluacion(id, "Examen 1 a", LocalDate.of(2025, 4, 30), 
        LocalDateTime.of(2025, 4, 30, 18, 0, 0), LocalDateTime.of(2025, 4, 30, 19, 0, 0), 
        100, "Seleccion multiple", EstadoEvaluacion.ACTIVO, null, null, "1- xXD",
         "No se", null);
        when(evaluacionRepository.findById(id)).thenReturn(Optional.of(existente));
        when(evaluacionRepository.save(existente)).thenReturn(existente);
        Optional<Evaluacion> resultado = evaluacionService.actualizarEvaluacion(id, actualizada);
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getTitulo()).isEqualTo("Examen 1 a");
        assertThat(resultado.get().getInstrucciones()).isEqualTo("1- xXD");
        verify(evaluacionRepository).findById(id);
        verify(evaluacionRepository).save(existente);
    }
    
    
}
