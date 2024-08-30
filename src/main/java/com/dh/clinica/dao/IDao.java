package com.dh.clinica.dao;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {
    T guardar(T t);
    T buscarPorId(Integer id);
    List<T> buscarTodos();
    void actualizar(T t);
    void eliminar(Integer id);
}
