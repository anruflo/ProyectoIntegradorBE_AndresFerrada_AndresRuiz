package com.dh.clinica.repository;

import com.dh.clinica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer> {
    public List<Odontologo> findByApellidoAndNombre(String apellido, String nombre);
    public List<Odontologo> findByNombre(String nombre);
    public List<Odontologo> findByApellido(String apellido);


    public List<Odontologo> findByMatricula(Integer matricula);
}
