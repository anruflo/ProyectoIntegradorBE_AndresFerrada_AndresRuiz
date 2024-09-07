package com.dh.clinica.service.Impl;

import com.dh.clinica.entity.Paciente;
import com.dh.clinica.exception.BadRequestException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.repository.IPacienteRepository;
import com.dh.clinica.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {
    private final Logger logger = LoggerFactory.getLogger(PacienteService.class);
    private final IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {

        validarPaciente(paciente);

        Paciente pacienteGuardado = pacienteRepository.save(paciente);

        logger.info("Paciente guardado: {}", pacienteGuardado);
        return pacienteGuardado;
    }

    @Override
    public Optional<Paciente> buscarPorId(Integer id) {
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(id);

        if(pacienteEncontrado.isPresent()){
            logger.info("Paciente encontradoo: {}", pacienteEncontrado.get());
        } else{
            logger.info("Paciente con id {} no ha sido encontrado", id);
        }

        return pacienteEncontrado;
    }

    @Override
    public List<Paciente> listarTodos() {
        List<Paciente> pacientes = pacienteRepository.findAll();

        if(pacientes.isEmpty()){
            logger.info("No se encontraron pacientes");
        } else {
            logger.info("Se encontraron {} pacientes en la lista", pacientes.size());
        }

        return pacientes;
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(paciente.getId());

        validarPaciente(paciente);

        if(pacienteEncontrado.isPresent()){
            Paciente pacienteActualizado = pacienteRepository.save(paciente);
            logger.info("Paciente actualizado: {}", pacienteActualizado);
        } else {
            logger.error("Paciente no encontrado. No se ha podido actualizar");
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    @Override
    public void eliminarPaciente(Integer id) {
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(id);

        if(pacienteEncontrado.isPresent()){
            pacienteRepository.deleteById(id);
            logger.info("Paciente con ID {} eliminado", id);
        } else{
            logger.error("Error al eliminar paciente con ID {}",id);
            throw new ResourceNotFoundException("Paciente " + id + " no encontrado");
        }
    }

    @Override
    public List<Paciente> buscarPorApellidoYNombre(String apellido, String nombre) {
        List<Paciente> pacienteEncontrado = pacienteRepository.findByApellidoAndNombre(apellido, nombre);

        if(pacienteEncontrado.isEmpty()){
            logger.info("Paciente no encontrado");
            throw new ResourceNotFoundException("Paciente no encontrado");
        }

        logger.info("Paciente {}, {} encontrado", apellido, nombre);
        return pacienteEncontrado;
    }

    @Override
    public List<Paciente> buscarLikeNombre(String nombre) {
        List<Paciente> pacientes = pacienteRepository.findByNombreLike(nombre);

        if(pacientes.isEmpty()){
            logger.info("No se encontraron pacientes con el nombre similar a: {}", nombre);
        } else{
            logger.info("Se encontraron {} pacientes con el nombre similar a: {}", pacientes.size(), nombre);
            logger.info("lista de pacientes encontrados: {}", pacientes);
        }

        return pacientes;
    }

    @Override
    public List<Paciente> buscarPorDni(String dni) {
        List<Paciente> pacienteEncontrado = pacienteRepository.findByDni(dni);

        if(pacienteEncontrado.isEmpty()){
            logger.info("No hay paciente con DNI {}", dni);
        } else {
            logger.info("Paciente con DNI {} encontrado", dni);
        }
        return pacienteEncontrado;
    }

    private void validarPaciente(Paciente paciente) {
        if(paciente.getNombre() == null || paciente.getNombre().isEmpty()){
            logger.error("Nombre no puede estar vacío");
            throw new BadRequestException("Nombre no puede estar vacío");
        }
        if(paciente.getApellido() == null || paciente.getApellido().isEmpty()){
            logger.error("Apellido no puede estar vacío");
            throw new BadRequestException("Apellido no puede estar vacío");
        }
        if(paciente.getDomicilio() == null){
            logger.error("Domicilio no puede estar vacío");
            throw new BadRequestException("Paciente debe tener domicilio");
        }
    }
}
