package com.dh.clinica.service.Impl;


import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.repository.IOdontologoRepository;
import com.dh.clinica.service.IOdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {
    private final Logger logger = LoggerFactory.getLogger(OdontologoService.class);
    private IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository iodontologoRepository) {
        odontologoRepository = iodontologoRepository;
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);
        logger.info("Odontologo guardado: " + odontologoGuardado.toString());
        return odontologoGuardado;
    }

    @Override
    public Optional<Odontologo> buscarPorId(Integer id) {
        Optional<Odontologo> odontologoEncontrado = odontologoRepository.findById(id);
        logger.info("Odontologo encontrado: " + odontologoEncontrado.get());
        return odontologoEncontrado;
    }

    @Override
    public List<Odontologo> listarTodos() {
        logger.info("Listando todos los odontologos");
        return odontologoRepository.findAll();
    }

    @Override
    public void actualizarOdontologo(Odontologo odontologo) {
        Odontologo odontologoActualizado = odontologoRepository.save(odontologo);
        logger.info("Odontologo actualizado: " + odontologoActualizado.toString());
    }

    @Override
    public void eliminarOdontologo(Integer id) {
        Optional<Odontologo> odontologoEncontrado = odontologoRepository.findById(id);

        if(odontologoEncontrado.isPresent()){
            odontologoRepository.deleteById(id);
            logger.info("Odontolodo con ID: " + id + " eliminado");
        } else {
            logger.info("Error al eliminar odontologo con ID: " + id);
            throw new ResourceNotFoundException("Odontólogo " + id + " no encontrado");
        }
    }

    @Override
    public List<Odontologo> buscarPorNumeroMatricula(Integer matricula) {
        logger.info("Buscando paciente con nuemero de matricula: " + matricula);
        return odontologoRepository.findByMatricula(matricula);
    }

    @Override
    public List<Odontologo> buscarPorApellidoYNombre(String apellido, String nombre) {
        logger.info("Buscando odontologos con apellido y nombre: " + apellido, nombre);
        return odontologoRepository.findByApellidoAndNombre(apellido, nombre);
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) {
        logger.info("Buscando odontologo con nombre: " + nombre);
        return odontologoRepository.findByNombre(nombre);
    }

    @Override
    public List<Odontologo> buscarPorApellido(String apellido) {
        logger.info("Buscando odontologo con apellido: " + apellido);
        return odontologoRepository.findByApellido(apellido);
    }
}
