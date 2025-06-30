package com.Edutech.Evaluaciones.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.Edutech.Evaluaciones.Model.EstadoEvaluacion;
import com.Edutech.Evaluaciones.Model.Evaluacion;
import com.Edutech.Evaluaciones.Service.EvaluacionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EvaluacionController.class)
public class EvaluacionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvaluacionService evaluacionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenertodas() throws Exception {
        Evaluacion ev1 = new Evaluacion(1, "Examen 1", LocalDate.of(2025, 4, 30), 
        LocalDateTime.of(2025, 4, 30, 18, 0, 0), LocalDateTime.of(2025, 4, 30, 19, 0, 0), 
        100, "Seleccion multiple", EstadoEvaluacion.ACTIVO, null, null, "1- XD",
         "No se", null);
        Evaluacion ev2 = new Evaluacion(2, "Examen 2", LocalDate.of(2025, 4, 30), 
        LocalDateTime.of(2025, 4, 30, 18, 0, 0), LocalDateTime.of(2025, 4, 30, 19, 0, 0), 
        100, "Seleccion multiple", EstadoEvaluacion.ACTIVO, null, null, "1- XD",
         "No se", null);
        
        Mockito.when(evaluacionService.listar()).thenReturn(Arrays.asList(ev1,ev2));

        mockMvc.perform(get("/api/evaluaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value(ev1.getTitulo()));
    }

    @Test
    void testCrearEvaluacion() throws Exception {
        Evaluacion e = new Evaluacion(0, "Examen 1", LocalDate.of(2025, 4, 30), 
        LocalDateTime.of(2025, 4, 30, 18, 0, 0), LocalDateTime.of(2025, 4, 30, 19, 0, 0), 
        100, "Seleccion multiple", EstadoEvaluacion.ACTIVO, null, null, "1- XD",
         "No se", null);
        Evaluacion guardada = new Evaluacion(1, "Examen 1", LocalDate.of(2025, 4, 30), 
        LocalDateTime.of(2025, 4, 30, 18, 0, 0), LocalDateTime.of(2025, 4, 30, 19, 0, 0), 
        100, "Seleccion multiple", EstadoEvaluacion.ACTIVO, null, null, "1- XD",
         "No se", null);
        
        Mockito.when(evaluacionService.crearEvaluacion(any(Evaluacion.class)))
                .thenReturn(guardada);
        
        mockMvc.perform(post("/api/evaluaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(e)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value(guardada.getTitulo()));
    }

    @Test
    void testbuscarxidnoexistente() throws Exception {
        Mockito.when(evaluacionService.buscarporID(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/evaluaciones/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testBuscarxid() throws Exception {
        int id = 1;
        Evaluacion simulada = new Evaluacion(id, "Examen 1", LocalDate.of(2025, 4, 30), 
        LocalDateTime.of(2025, 4, 30, 18, 0, 0), LocalDateTime.of(2025, 4, 30, 19, 0, 0), 
        100, "Seleccion multiple", EstadoEvaluacion.ACTIVO, null, null, "1- XD",
         "No se", null);
        
        Mockito.when(evaluacionService.buscarporID(id)).thenReturn(Optional.of(simulada));
        mockMvc.perform(get("/api/evaluaciones/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.titulo").value(simulada.getTitulo()))
        .andExpect(jsonPath("$.fecha").value("2025-04-30"))
        .andExpect(jsonPath("$.horainicio").value("2025-04-30T18:00:00"))
        .andExpect(jsonPath("$.horafin").value("2025-04-30T19:00:00"))
        .andExpect(jsonPath("$.puntajetotal").value(simulada.getPuntajetotal()))
        .andExpect(jsonPath("$.tipo").value(simulada.getTipo()))
        .andExpect(jsonPath("$.estado").value("ACTIVO"))
        .andExpect(jsonPath("$.instrucciones").value(simulada.getInstrucciones()))
        .andExpect(jsonPath("$.habilidadesevaluadas").value(simulada.getHabilidadesevaluadas()));
    }

    @Test
    void testEliminarEvaluacion() throws Exception {
        int id = 1;
        Mockito.doNothing().when(evaluacionService).eliminarEvaluacion(id);
        mockMvc.perform(delete("/api/evaluaciones/{id}", id))
                .andExpect(status().isNoContent()); 
}

@Test
void testActualizarEvaluacion() throws Exception {
    int id = 1;
    Evaluacion actualizada = new Evaluacion(id, "Examen 1 a", LocalDate.of(2025, 4, 30), 
        LocalDateTime.of(2025, 4, 30, 18, 0, 0), LocalDateTime.of(2025, 4, 30, 19, 0, 0), 
        100, "Seleccion multiple", EstadoEvaluacion.ACTIVO, null, null, "1- xXD",
        "No se", null);

    Mockito.when(evaluacionService.actualizarEvaluacion(eq(id), any(Evaluacion.class)))
           .thenReturn(Optional.of(actualizada));

    mockMvc.perform(put("/api/evaluaciones/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(actualizada)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.titulo").value("Examen 1 a"))
            .andExpect(jsonPath("$.instrucciones").value("1- xXD"));
}
}
