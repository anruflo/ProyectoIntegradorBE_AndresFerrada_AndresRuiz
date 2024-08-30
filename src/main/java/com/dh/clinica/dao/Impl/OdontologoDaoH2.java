package com.dh.clinica.dao.Impl;

import com.dh.clinica.dao.IDao;
import com.dh.clinica.db.H2Connection;
import com.dh.clinica.model.Odontologo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OdontologoDaoH2 implements IDao<Odontologo> {
    private static final Logger logger = LoggerFactory.getLogger(OdontologoDaoH2.class);
    private static final String INSERT = "INSERT INTO ODONTOLOGOS VALUES(DEFAULT, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM ODONTOLOGOS";
    private static final String SELECT_ID = "SELECT * FROM ODONTOLOGOS WHERE ID = ?";
    public static final String UPDATE = "UPDATE ODONTOLOGOS SET MATRICULA=?, NOMBRE=?, APELLIDO=? WHERE ID=?";
    public static final String DELETE = "DELETE FROM ODONTOLOGOS WHERE ID = ?";


    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Odontologo odontologoARetornar = null;

        try {
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, odontologo.getMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());

            preparedStatement.executeUpdate();
            connection.commit();

            resultSet = preparedStatement.getGeneratedKeys();
            Integer id = null;

            while(resultSet.next()){
                id = resultSet.getInt(1);
                odontologoARetornar = new Odontologo(id, odontologo.getMatricula(), odontologo.getNombre(), odontologo.getApellido());
            }


            logger.info("Odontólogo guardado con éxito: " + odontologoARetornar);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();

            if(connection != null){
                try{
                    connection.rollback();
                    logger.info("La transacción se ha revertido");
                } catch(SQLException ex) {
                    logger.error("No se ha podido ejecutar el rollback", ex);
                }
            }
        } finally{
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("No se ha podido cerrar la conexión", e);
            }
        }

        return odontologoARetornar;
    }

    @Override
    public Odontologo buscarPorId(Integer id) {
        Connection connection = null;
        Odontologo odontologoEncontrado = null;

        try{
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                Integer matricula = resultSet.getInt(2);
                String nombre = resultSet.getString(3);
                String apellido = resultSet.getString(4);
                odontologoEncontrado = new Odontologo(id, matricula, nombre, apellido);
            }
            if(odontologoEncontrado != null){
                logger.info("Odontólogo encontrado: " + odontologoEncontrado);
            } else {
                logger.info("Odontólogo no encontrado");
            }
        } catch (Exception e){
            logger.error("No se ha podido encontrar el paciente", e);
        } finally {
            try{
                connection.close();
            } catch (SQLException ex){
                logger.error("No se ha podido cerrar la conexión", ex);
            }
        }

        return odontologoEncontrado;

    }

    @Override
    public List<Odontologo> buscarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();
        Odontologo odontologo = null;

        try {
            connection = H2Connection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);


            while (resultSet.next()){
                Integer id = resultSet.getInt(1);
                Integer matricula = resultSet.getInt(2);
                String nombre = resultSet.getString(3);
                String apellido = resultSet.getString(4);

                odontologo = new Odontologo(id, matricula, nombre, apellido);

                logger.info("Odontólogo: " + odontologo);

                odontologos.add(odontologo);
            }

        }catch (Exception e){
            logger.error("No se ha podido realizar la consulta: ", e);

        } finally{
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("No se ha podido cerrar la conexión: ", e);
            }

        }

        return odontologos;
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        Connection connection = null;

        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setInt(1, odontologo.getMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.setInt(4, odontologo.getId());

            preparedStatement.executeUpdate();
            connection.commit();

            logger.info("Odontólogo actualizado: " + odontologo);

        }catch (Exception e){
            logger.error("No se ha podido actualizar el odontólogo: ", e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("No se ha podido revertir la transacción: ", ex);
            }
        }finally {
            try{
                connection.close();
            } catch (SQLException e){
                logger.error("No se ha podido cerrar la conexión: ", e);
            }
        }

    }

    @Override
    public void eliminar(Integer id) {

        Connection connection = null;
        Odontologo odontologo = buscarPorId(id);

        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            if(odontologo != null){
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                connection.commit();
                logger.info("Odontólogo eliminado: " + odontologo);
            }

        }catch(Exception e){
            logger.error("No se ha podido eliminar el odontólogo: ", e);

            try{
                connection.rollback();
            }catch(SQLException ex){
                logger.error("No se ha podido revertir la transacción: ", ex);
            } finally {
                try{
                    connection.setAutoCommit(true);
                }catch (SQLException ex){
                    logger.error("No se ha podido desactivar el autocommit: ", ex);
                }
            }
        }finally {
            try{
                connection.close();
            } catch (SQLException e){
                logger.error("No se ha podido cerrar la conexión: ", e);
            }
        }
    }

}
