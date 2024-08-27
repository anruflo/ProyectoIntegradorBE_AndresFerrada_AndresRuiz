package com.dh.clinica.service;

import com.dh.clinica.dao.IDao;
import com.dh.clinica.model.Paciente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    private IDao<Paciente> pacienteIDao;

    public PacienteService(IDao<Paciente> pacienteIDao){
        this.pacienteIDao = pacienteIDao;
    }

    public Paciente guardarPaciente(Paciente paciente){
        return pacienteIDao.registrar(paciente);
    }

    public Paciente buscarPorId(Integer id){
        return pacienteIDao.buscarPorId(id);
    }

    public List<Paciente> listarTodos(){
        return pacienteIDao.buscarTodos();
    }

    public void actualizarPaciente(Paciente paciente){
        pacienteIDao.actualizar(paciente);
    }

    public void eliminarPaciente(Integer id){
        pacienteIDao.eliminar(id);
    }

}
