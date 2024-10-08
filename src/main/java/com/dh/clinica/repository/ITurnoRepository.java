package com.dh.clinica.repository;

import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Integer> {

    @Query("select t from Turno t join t.odontologo o where o.apellido = :apellidoOdontologo")
    List<Turno> buscarTurnoPorApellidoOdontologo(String apellidoOdontologo);

    @Query("select t from Turno t join t.paciente p where p.apellido = :apellidoPaciente")
    List<Turno> buscarTurnoPorApellidoPaciente(String apellidoPaciente);

    @Query("select t from Turno t join t.paciente p where p.dni = :dniPaciente")
    List<Turno> buscarTurnoDniPaciente(String dniPaciente);
    @Query("select t from Turno t join t.odontologo o where o.matricula = :matricula")
    List<Turno> buscarTurnoPorMatriculaOdontologo(Integer matricula);

}
