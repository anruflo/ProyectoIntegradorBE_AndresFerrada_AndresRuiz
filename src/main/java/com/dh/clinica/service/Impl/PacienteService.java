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
    private IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) {

        if(paciente.getNombre() == null || paciente.getNombre().isEmpty()){
            throw new BadRequestException("Nombre no puede estar vacío");
        }
        if(paciente.getApellido() == null || paciente.getApellido().isEmpty()){
            throw new BadRequestException("Apellido no puede estar vacío");
        }
        if(paciente.getDomicilio() == null){
            throw new BadRequestException("Paciente debe tener domicilio");
        }


        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        logger.info("Paciente guardado: " + pacienteGuardado.toString());
        return pacienteGuardado;
    }

    @Override
    public Optional<Paciente> buscarPorId(Integer id) {
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(id);
        logger.info("Paciente encontrado: {}", pacienteEncontrado.get());
        return pacienteEncontrado;
    }

    @Override
    public List<Paciente> listarTodos() {
        List<Paciente> pacientes = pacienteRepository.findAll();

        if(pacientes.isEmpty()){
            logger.info("No se encontraron pacientes");
        } else {
            logger.info("Se encontraron {} pacientes", pacientes.size());
        }

        return pacientes;
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        Paciente pacienteActualizado = pacienteRepository.save(paciente);
        logger.info("Paciente actualizado: " + pacienteActualizado.toString());
    }

    @Override
    public void eliminarPaciente(Integer id) {
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(id);

        if (!pacienteEncontrado.isPresent()) {
            logger.error("Error al eliminar paciente con ID {}",id);
            throw new ResourceNotFoundException("Paciente " + id + " no encontrado");
        }

        pacienteRepository.deleteById(id);
        logger.info("Paciente con ID {} eliminado", id);
    }

    @Override
    public List<Paciente> buscarPorApellidoYNombre(String apellido, String nombre) {
        List<Paciente> pacienteEncontrado = pacienteRepository.findByApellidoAndNombre(apellido, nombre);

        if(pacienteEncontrado.isEmpty()){
            logger.info("Paciente no encontrado");
            throw new ResourceNotFoundException("Paciente no encontrado");
        }

        logger.info("Paciente " + apellido + ", " + nombre + " encontrado");
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
            logger.info("Paciente con dni {} no ha sido encontrado", dni);
        } else {
            logger.info("Paciente con dni {} encontrado", dni);
        }
        return pacienteEncontrado;
    }

}
