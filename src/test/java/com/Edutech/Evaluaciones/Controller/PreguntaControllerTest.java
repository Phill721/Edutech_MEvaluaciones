package com.Edutech.Evaluaciones.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.Edutech.Evaluaciones.Model.Pregunta;
import com.Edutech.Evaluaciones.Service.PreguntaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PreguntaController.class)
public class PreguntaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PreguntaService preguntaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarPreguntas() throws Exception {
        Pregunta p1 = new Pregunta(1, "ASD", null, 1, 10, "Bien hecho", null);
        Pregunta p2 = new Pregunta(2, "ASDF", null, 1, 10, "Bien hecho", null);

        when(preguntaService.listar()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/preguntas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].enunciado").value("ASD"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].enunciado").value("ASDF"));
    }

    @Test
    void testCrearPregunta() throws Exception {
        Pregunta pregunta = new Pregunta(0, "ASD", null, 1, 10, "Bien hecho", null);
        Pregunta guardada = new Pregunta(1, "ASD", null, 1, 10, "Bien hecho", null);

        when(preguntaService.crearPregunta(any(Pregunta.class))).thenReturn(guardada);

        mockMvc.perform(post("/api/preguntas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pregunta)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.enunciado").value("ASD"));
    }

    @Test
    void testbuscarxidnoexistente() throws Exception {
        Mockito.when(preguntaService.buscarxid(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/preguntas/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testBuscarxid() throws Exception {
        int id = 1;
        Pregunta simulado = new Pregunta(id, "ASD", null, 1, 10, "Bien hecho", null);

        when(preguntaService.buscarxid(id)).thenReturn(Optional.of(simulado));

        mockMvc.perform(get("/api/preguntas/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.enunciado").value("ASD"))
                .andExpect(jsonPath("$.opciones").isEmpty())
                .andExpect(jsonPath("$.nrorespuestacorrecta").value(1))
                .andExpect(jsonPath("$.puntos").value(10))
                .andExpect(jsonPath("$.feedback").value("Bien hecho"))
                .andExpect(jsonPath("$.evaluacion").doesNotExist());
    }

    @Test
    void testEliminarPregunta() throws Exception {
        int id = 1;

        doNothing().when(preguntaService).eliminarPregunta(id);

        mockMvc.perform(delete("/api/preguntas/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void testActualizarPregunta() throws Exception {
        int id = 1;
        Pregunta actualizada = new Pregunta(id, "QWE", null, 2, 15, "Bien hecho", null);

        when(preguntaService.actualizarPregunta(eq(id), any(Pregunta.class)))
            .thenReturn(Optional.of(actualizada));

        mockMvc.perform(put("/api/preguntas/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizada)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.enunciado").value("QWE"))
            .andExpect(jsonPath("$.nrorespuestacorrecta").value(2))
            .andExpect(jsonPath("$.puntos").value(15));
    }
}
