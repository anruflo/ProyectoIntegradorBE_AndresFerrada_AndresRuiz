package com.dh.clinica.service;

import com.dh.clinica.entity.Odontologo;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {

    Odontologo guardarOdontologo(Odontologo odontologo);
    Optional<Odontologo> buscarPorId(Integer id);

    List<Odontologo> listarTodos();

    void actualizarOdontologo(Odontologo odontologo);

    void eliminarOdontologo(Integer id);

    List<Odontologo> buscarPorApellidoYNombre(String apellido, String nombre);
    List<Odontologo> buscarPorNombre(String nombre);
    List<Odontologo> buscarPorApellido(String apellido);


    List<Odontologo> buscarPorNumeroMatricula(Integer matricula);
}
