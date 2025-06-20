package com.Edutech.Evaluaciones.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Edutech.Evaluaciones.Model.Pregunta;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Integer>{
    
}
