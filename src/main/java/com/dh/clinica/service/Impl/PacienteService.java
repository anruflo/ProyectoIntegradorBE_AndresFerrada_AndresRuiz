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
        logger.info("Paciente encontrado: " + pacienteEncontrado.get());
        return pacienteEncontrado;
    }

    @Override
    public List<Paciente> listarTodos() {
        logger.info("Listando todos los pacientes");
        return pacienteRepository.findAll();
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        Paciente pacienteActualizado = pacienteRepository.save(paciente);
        logger.info("Paciente actualizado: " + pacienteActualizado.toString());
    }

    @Override
    public void eliminarPaciente(Integer id) {
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(id);

        if (pacienteEncontrado.isPresent()) {
            pacienteRepository.deleteById(id);
            logger.info("Paciente con ID: " + id +" eliminado");
        } else {
            logger.error("Error al eliminar paciente con ID: " + id);
            throw new ResourceNotFoundException("Paciente " + id + " no encontrado");
        }
    }

    @Override
    public List<Paciente> buscarPorApellidoYNombre(String apellido, String nombre) {
        logger.info("Buscando pacientes con apellido y nombre: " + apellido, nombre);
        return pacienteRepository.findByApellidoAndNombre(apellido, nombre);
    }

    @Override
    public List<Paciente> buscarLikeNombre(String nombre) {
        logger.info("Buscando pacientes con nombre: " + nombre);
        return pacienteRepository.findByNombreLike(nombre);
    }

    @Override
    public List<Paciente> buscarPorDni(String dni) {
        logger.info("Buscando paciente con DNI: " + dni);
        return pacienteRepository.findByDni(dni);
    }

}
