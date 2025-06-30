package com.Edutech.Evaluaciones.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Pregunta_evaluacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 300, nullable = false)
    private String enunciado;

    @ElementCollection
    private List<String> opciones = new ArrayList<>();

    @Column(nullable = false)
    private int nrorespuestacorrecta;

    @Column(nullable = false)
    private int puntos;

    @Lob
    @Column(nullable = true)
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "evaluacion_id")
    @JsonBackReference
    private Evaluacion evaluacion;

    public boolean escorrecta(int respuesta){
        return this.nrorespuestacorrecta==respuesta;
    }
}
