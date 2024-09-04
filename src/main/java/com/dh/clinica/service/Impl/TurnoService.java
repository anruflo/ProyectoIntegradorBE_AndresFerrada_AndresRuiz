package com.dh.clinica.service.Impl;


import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.exception.ResourceNotFoundException;
import com.dh.clinica.repository.ITurnoRepository;
import com.dh.clinica.service.ITurnoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
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
        }

        return turnoARetornar;
    }

    @Override
    public Optional<Turno> buscarPorId(Integer id) {
        return turnoRepository.findById(id);
    }

    @Override
    public List<Turno> listarTodos() {
        return turnoRepository.findAll();
    }

    @Override
    public void actualizarTurno(Turno turno) {
        Optional<Paciente> paciente = pacienteService.buscarPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(turno.getOdontologo().getId());

        if (paciente.isPresent() && odontologo.isPresent()) {
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turnoRepository.save(turno);
        }
    }

    @Override
    public void eliminarTurno(Integer id) {
        Optional<Turno> turno = turnoRepository.findById(id);

        if (turno.isPresent()) {
            turnoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("{\"mensaje\": \"Turno no encontrado\"}");
        }

        turnoRepository.deleteById(id);
    }

    @Override
    public List<Turno> buscarTurnoPaciente(String apellidoPaciente) {
        return turnoRepository.buscarTurnoPorApellidoPaciente(apellidoPaciente);
    }

}
