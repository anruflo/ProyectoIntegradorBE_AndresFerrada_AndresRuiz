package com.dh.clinica.service;


import com.dh.clinica.dao.IDao;
import com.dh.clinica.model.Odontologo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService {
    private IDao<Odontologo> OdontologoIdao;

    public OdontologoService(IDao<Odontologo> odontologoIdao) {
        OdontologoIdao = odontologoIdao;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return OdontologoIdao.guardar(odontologo);
    }

    public Odontologo buscarPorId(Integer id) {
        return OdontologoIdao.buscarPorId(id);
    }

    public List<Odontologo> listarTodos() {
        return OdontologoIdao.buscarTodos();
    }

    public void actualizarOdontologo(Odontologo odontologo) {
        OdontologoIdao.actualizar(odontologo);
    }

    public void eliminarOdontologo(Integer id) {
        OdontologoIdao.eliminar(id);
    }

}
