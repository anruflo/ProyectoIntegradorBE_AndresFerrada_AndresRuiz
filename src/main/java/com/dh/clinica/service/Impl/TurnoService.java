package com.dh.clinica.service.Impl;


import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.exception.BadRequestException;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.repository.ITurnoRepository;
import com.dh.clinica.service.ITurnoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
    private final Logger logger = LoggerFactory.getLogger(TurnoService.class);
    private final ITurnoRepository turnoRepository;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    public TurnoService(ITurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public Turno guardarTurno(Turno turno) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turno.getOdontologo().getId());

        Turno turnoARetornar;

        if(turno.getFecha() == null || turno.getFecha().isBefore(LocalDate.now())) {
            logger.error("No se puede guardar turno. La fecha no puede ser anterior a la actual");
            throw new BadRequestException("La fecha no puede ser anterior a la actual");
        }

        if (paciente.isPresent() && odontologo.isPresent()) {
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turnoARetornar = turnoRepository.save(turno);

            logger.info("Turno guardado: {}", turnoARetornar);
        } else{
            logger.error("No se puede guardar turno. Paciente u odontólogo no encontrado");
            throw new ResourceNotFoundException("Paciente u odontólogo no encontrados");
        }

        return turnoARetornar;
    }

    @Override
    public Optional<Turno> buscarPorId(Integer id) {
        Optional<Turno> turnoEncontrado = turnoRepository.findById(id);

        if(turnoEncontrado.isPresent()){
            logger.info("Turno encontrado: {}", turnoEncontrado.get());
        } else {
            logger.info("Turno con id {} no ha sido encontrado", id);
            throw new ResourceNotFoundException("Turno " + id + " no encontrado");
        }
        return turnoEncontrado;
    }

    @Override
    public List<Turno> listarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        logger.info("turnos: {}", turnos);
        return turnoRepository.findAll();
    }

    @Override
    public void actualizarTurno(Turno turno) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turno.getOdontologo().getId());

        if (paciente.isPresent() && odontologo.isPresent()) {
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            Turno turnoActualizado = turnoRepository.save(turno);
            logger.info("Turno actualizado: {}", turnoActualizado);
        }
    }

    @Override
    public void eliminarTurno(Integer id) {
        Optional<Turno> turno = turnoRepository.findById(id);

        if (turno.isPresent()) {
            turnoRepository.deleteById(id);
            logger.info("Turno con ID {} eliminado", id);
        } else {
            logger.info("Error al eliminar turno con ID {}", id);
            throw new ResourceNotFoundException("Turno " + id + " no encontrado");
        }
    }

    @Override
    public List<Turno> buscarTurnoOdontologo(String apellidoOdontologo) {
        List<Turno> turnos = turnoRepository.buscarTurnoPorApellidoOdontologo(apellidoOdontologo);

        if(turnos.isEmpty()){
            logger.info("No se encontraron turnos para el odontólogo con apellido: {}", apellidoOdontologo);
            throw new ResourceNotFoundException("No se encontraron turnos para el odontólogo con apellido: " + apellidoOdontologo);
        }

        if(turnos.size() == 1){
            logger.info("Se encontró {} turno para el odontólogo con apellido {}", turnos.size(), apellidoOdontologo);
        } else {
            logger.info("Se encontraron {} turnos para el odontólogo con apellido {}", turnos.size(), apellidoOdontologo);
        }

        return turnos;
    }

    @Override
    public List<Turno> buscarTurnoPaciente(String apellidoPaciente) {
        List<Turno> turnos = turnoRepository.buscarTurnoPorApellidoPaciente(apellidoPaciente);

        if(turnos.isEmpty()){
            logger.info("No se encontraron turnos para el paciente con apellido: {}", apellidoPaciente);
            throw new ResourceNotFoundException("No se encontraron turnos para el paciente con apellido: " + apellidoPaciente);
        }

        if(turnos.size() == 1){
            logger.info("Se encontró {} turno para el paciente con apellido {}", turnos.size(), apellidoPaciente);
        } else {
            logger.info("Se encontraron {} turnos para el paciente con apellido {}", turnos.size(), apellidoPaciente);
        }

        return turnos;
    }

}
