package com.dh.clinica.service;

import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Turno;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ITurnoService {
    Turno guardarTurno(Turno turno);

    Optional<Turno> buscarPorId(Integer id);

    List<Turno> listarTodos();

    void actualizarTurno(Turno turno);

    void eliminarTurno(Integer id);

    List<Turno> buscarTurnoOdontologo(String apellidoOdontologo);
    List<Turno> buscarTurnoPaciente(String apellidoPaciente);
    @Query("select t from Turno t join t.paciente p where p.dni = :dniPaciente")
    List<Turno> buscarTurnoDniPaciente(String dniPaciente);
    @Query("select t from Turno t join t.odontologo o where o.matricula = :matricula")
    List<Turno> buscarTurnoPorMatriculaOdontologo(Integer matricula);
}
