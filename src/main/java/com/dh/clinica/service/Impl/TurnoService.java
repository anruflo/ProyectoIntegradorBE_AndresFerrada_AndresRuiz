package com.dh.clinica.service.Impl;


import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.repository.ITurnoRepository;
import com.dh.clinica.service.ITurnoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
    // hacer Logger en cada uno de los servicios
    private final Logger logger = LoggerFactory.getLogger(TurnoService.class);
    private ITurnoRepository turnoRepository;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    public TurnoService(ITurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public Turno guardarTurno(Turno turno) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turno.getOdontologo().getId());

        Turno turnoARetornar = null;

        if (paciente.isPresent() && odontologo.isPresent()) {
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turnoARetornar = turnoRepository.save(turno);

            logger.info("turno guardado: " + turnoARetornar);
        }

        return turnoARetornar;
    }

    @Override
    public Optional<Turno> buscarPorId(Integer id) {
        Optional<Turno> turnoEncontrado = turnoRepository.findById(id);
        logger.info("Turno encontrado: " + turnoEncontrado.get());
        return turnoEncontrado;
    }

    @Override
    public List<Turno> listarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        logger.info("turnos: " + turnos);
        return turnoRepository.findAll();
    }

    @Override
    public void actualizarTurno(Turno turno) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turno.getOdontologo().getId());

        Turno turnoActualizado = null;

        if (paciente.isPresent() && odontologo.isPresent()) {
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turnoActualizado = turnoRepository.save(turno);
            logger.info("Turno actualizado: " + turnoActualizado);
        }
    }

    @Override
    public void eliminarTurno(Integer id) {
        Optional<Turno> turno = turnoRepository.findById(id);

        if (turno.isPresent()) {
            turnoRepository.deleteById(id);
            logger.info("Turno con ID: " + id + " eliminado");
        } else {
            logger.info("Error al eliminar turno con ID: " + id);
            throw new ResourceNotFoundException("Turno " + id + " no encontrado");
        }
    }

    @Override
    public List<Turno> buscarTurnoPaciente(String apellidoPaciente) {
        logger.info("Buscando turnos de pacientes con apellido: " + apellidoPaciente);
        return turnoRepository.buscarTurnoPorApellidoPaciente(apellidoPaciente);
    }

}
