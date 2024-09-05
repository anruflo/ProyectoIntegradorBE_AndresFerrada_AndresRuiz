package com.dh.clinica.repository;

import com.dh.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {
    public List<Paciente> findByApellidoAndNombre(String apellido, String nombre);

    @Query("select p from Paciente p where p.nombre like %:nombre%")
    List<Paciente> findByNombreLike(String nombre);

    public List<Paciente> findByDni(String dni);
}