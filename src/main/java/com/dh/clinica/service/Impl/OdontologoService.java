package com.dh.clinica.service.Impl;


import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.exception.BadRequestException;
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
    private final IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository iodontologoRepository) {
        odontologoRepository = iodontologoRepository;
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        validarOdontologo(odontologo);
        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);

        logger.info("Odontólogo guardado: {}", odontologoGuardado);
        return odontologoGuardado;
    }

    @Override
    public Optional<Odontologo> buscarPorId(Integer id) {
        Optional<Odontologo> odontologoEncontrado = odontologoRepository.findById(id);

        if(odontologoEncontrado.isPresent()){
            logger.info("Odontólogo encontrado: {}", odontologoEncontrado.get());
        } else{
            logger.info("Odontólogo con id {} no ha sido encontrado", id);
        }

        return odontologoEncontrado;
    }

    @Override
    public List<Odontologo> listarTodos() {
        List<Odontologo> odontologos = odontologoRepository.findAll();

        if(odontologos.isEmpty()){
            logger.info("No se encontraron odontólogos");
        } else {
            logger.info("Se encontraron {} odontológos en la lista", odontologos.size());
        }

        return odontologos;
    }

    @Override
    public void actualizarOdontologo(Odontologo odontologo) {
        Optional<Odontologo> odontologoEncontrado = odontologoRepository.findById(odontologo.getId());

        validarOdontologo(odontologo);

        if(odontologoEncontrado.isPresent()){
            Odontologo odontologoActualizado = odontologoRepository.save(odontologo);
            logger.info("Odontólogo actualizado: {}", odontologoActualizado);
        } else{
            logger.info("Odontólogo no encontrado. No se ha podido actualizar");
            throw new ResourceNotFoundException("Odontólogo no encontrado");
        }
    }

    @Override
    public void eliminarOdontologo(Integer id) {
        Optional<Odontologo> odontologoEncontrado = odontologoRepository.findById(id);

        if(odontologoEncontrado.isPresent()){
            odontologoRepository.deleteById(id);
            logger.info("Odontólodo con ID {} eliminado", id);
        } else {
            logger.info("Error al eliminar odontólogo con ID {}", id);
            throw new ResourceNotFoundException("Odontólogo " + id + " no encontrado");
        }
    }

    @Override
    public List<Odontologo> buscarPorNumeroMatricula(Integer matricula) {
        List<Odontologo> odontologoEncontrado = odontologoRepository.findByMatricula(matricula);

        if(odontologoEncontrado.isEmpty()){
            logger.info("No hay odontólogos con la matricula {}", matricula);
            throw new ResourceNotFoundException("No hay odontólogos con la matricula " + matricula);
        }

        logger.info("Hay {} odontólogos con la matricula {}", odontologoEncontrado.size(), matricula);
        return odontologoEncontrado;
    }

    @Override
    public List<Odontologo> buscarPorApellidoYNombre(String apellido, String nombre) {
        List<Odontologo> odontologoEncontrado = odontologoRepository.findByApellidoAndNombre(apellido, nombre);

        if(odontologoEncontrado.isEmpty()){
            logger.info("No hay odontólogos con el apellido {} y el nombre {}", apellido, nombre);
            throw new ResourceNotFoundException("No hay odontólogos con el apellido " + apellido + " y el nombre " + nombre);
        }

        logger.info("Hay {} odontólogos con el apellido {} y el nombre {}", odontologoEncontrado.size(), apellido, nombre);
        return odontologoEncontrado;
    }

    @Override
    public List<Odontologo> buscarPorNombre(String nombre) {
        List<Odontologo> odontologoEncontrado = odontologoRepository.findByNombre(nombre);

        if(odontologoEncontrado.isEmpty()){
            logger.info("No hay odontólogos con el nombre {}", nombre);
            throw new ResourceNotFoundException("No hay odontólogos con el nombre " + nombre);
        }
        logger.info("Hay {} odontólogos con el nombre {}", odontologoEncontrado.size(), nombre);
        return odontologoEncontrado;
    }

    @Override
    public List<Odontologo> buscarPorApellido(String apellido) {
        List<Odontologo> odontologoEncontrado = odontologoRepository.findByApellido(apellido);

        if(odontologoEncontrado.isEmpty()){
            logger.info("No hay odontólogos con el apellido {}", apellido);
            throw new ResourceNotFoundException("No hay odontólogos con el apellido " + apellido);
        }
        logger.info("Hay {} odontólogos con el apellido {}", odontologoEncontrado.size(), apellido);
        return odontologoEncontrado;
    }


    private void validarOdontologo(Odontologo odontologo) {
        if(odontologo.getNombre() == null || odontologo.getNombre().isEmpty()){
            logger.error("Nombre no puede estar vacío");
            throw new BadRequestException("Nombre no puede estar vacío");
        }
        if(odontologo.getApellido() == null || odontologo.getApellido().isEmpty()){
            logger.error("Apellido no puede estar vacío");
            throw new BadRequestException("Apellido no puede estar vacío");
        }
        if(odontologo.getMatricula() == null || odontologo.getMatricula() <= 0){
            logger.error("La matrícula no puede ser nula ni menor o igual a cero");
            throw new BadRequestException("La matrícula no puede ser nula ni menor o igual a cero");
        }
    }
}
