package com.dh.clinica.dao.Impl;

import com.dh.clinica.dao.IDao;
import com.dh.clinica.db.H2Connection;
import com.dh.clinica.model.Domicilio;
import com.dh.clinica.model.Paciente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PacienteDaoH2 implements IDao<Paciente> {
    public static final Logger logger = LoggerFactory.getLogger(PacienteDaoH2.class);

    public static final String INSERT = "INSERT INTO PACIENTES VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    public static final String SELECT_ID = "SELECT * FROM PACIENTES WHERE ID = ?";
    public static final String SELECT_ALL = "SELECT * FROM PACIENTES";
    public static final String UPDATE = "UPDATE PACIENTES SET APELLIDO=?, NOMBRE=?, DNI=?, FECHA_INGRESO=?, ID_DOMICILIO=? WHERE ID=?";
    public static final String DELETE = "DELETE FROM PACIENTES WHERE ID = ?";

    public DomicilioDaoH2 domicilioDaoH2 = new DomicilioDaoH2();

    @Override
    public Paciente guardar(Paciente paciente) {
        Connection connection = null;
        Paciente pacienteARetornar = null;
        Domicilio domicilio = domicilioDaoH2.guardar(paciente.getDomicilio());

       try{
         connection = H2Connection.getConnection();
         connection.setAutoCommit(false);

           PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

           preparedStatement.setString(1, paciente.getApellido());
           preparedStatement.setString(2, paciente.getNombre());
           preparedStatement.setString(3, paciente.getDni());
           preparedStatement.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
           preparedStatement.setInt(5, domicilio.getId());

           preparedStatement.executeUpdate();
           connection.commit();

           ResultSet resultSet = preparedStatement.getGeneratedKeys();

           if(resultSet.next()){
               Integer id = resultSet.getInt(1);
               pacienteARetornar = new Paciente(id, paciente.getApellido(), paciente.getNombre(), paciente.getDni(), paciente.getFechaIngreso(), domicilio);
           }

           logger.info("Paciente guardado " + pacienteARetornar);
       }catch (Exception e){
           logger.error("No se ha podido guardar el paciente", e);
           try{
               connection.rollback();
           } catch (SQLException ex){
               logger.error("No se ha podido ejecutar el rollback", ex);
           } finally {
               try{
                   connection.setAutoCommit(true);
               } catch (SQLException ex){
                   logger.error("No se ha podido desactivar el autocommit", ex);
               }
           }

       } finally {
           try{
               connection.close();
           } catch (SQLException ex){
               logger.error("No se ha podido cerrar la conexión", ex);
           }

       }

        return pacienteARetornar;
    }

    @Override
    public Paciente buscarPorId(Integer id) {
        Connection connection = null;
        Paciente pacienteEncontrado = null;

        try{
            connection = H2Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Integer idPaciente = resultSet.getInt(1);
                String apellido = resultSet.getString(2);
                String nombre = resultSet.getString(3);
                String dni = resultSet.getString(4);
                LocalDate fechaIngreso = resultSet.getDate(5).toLocalDate();
                Integer idDomicilio = resultSet.getInt(6);
                Domicilio domicilio = domicilioDaoH2.buscarPorId(idDomicilio);
                pacienteEncontrado = new Paciente(idPaciente, apellido, nombre, dni, fechaIngreso, domicilio);
            }
            if(pacienteEncontrado != null){
                logger.info("Paciente encontrado: " + pacienteEncontrado);
            } else {
                logger.info("Paciente no encontrado");
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

        return pacienteEncontrado;
    }

    @Override
    public List<Paciente> buscarTodos() {
        Connection connection = null;
        List<Paciente> pacientes = new ArrayList<>();
        Paciente paciente = null;

        try {
            connection = H2Connection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()){

                Integer idPaciente = resultSet.getInt(1);
                String apellido = resultSet.getString(2);
                String nombre = resultSet.getString(3);
                String dni = resultSet.getString(4);
                LocalDate fechaIngreso = resultSet.getDate(5).toLocalDate();
                Integer idDomicilio = resultSet.getInt(6);
                Domicilio domicilio = domicilioDaoH2.buscarPorId(idDomicilio);
                paciente = new Paciente(idPaciente, apellido, nombre, dni, fechaIngreso, domicilio);

                logger.info("Paciente: " + paciente);

                pacientes.add(paciente);
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

        return pacientes;
    }

    @Override
    public void actualizar(Paciente paciente) {
        Connection connection = null;
        domicilioDaoH2.actualizar(paciente.getDomicilio());

        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);

            preparedStatement.setString(1, paciente.getApellido());
            preparedStatement.setString(2, paciente.getNombre());
            preparedStatement.setString(3, paciente.getDni());
            preparedStatement.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            preparedStatement.setInt(5, paciente.getDomicilio().getId());
            preparedStatement.setInt(6, paciente.getId());

            preparedStatement.executeUpdate();
            connection.commit();

        }catch (Exception e){
            logger.error("No se ha podido actualizar el paciente", e);
            try{
                connection.rollback();
            } catch (SQLException ex){
                logger.error("No se ha podido ejecutar el rollback", ex);
            } finally {
                try{
                    connection.setAutoCommit(true);
                } catch (SQLException ex){
                    logger.error("No se ha podido desactivar el autocommit", ex);
                }
            }
        }finally {
            try{
                connection.close();
            } catch (SQLException ex){
                logger.error("No se ha podido cerrar la conexión", ex);
            }
        }
    }

    @Override
    public void eliminar(Integer id) {
        Connection connection = null;
        Paciente pacienteAEliminar = buscarPorId(id);

        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            if(pacienteAEliminar != null){
                domicilioDaoH2.eliminar(pacienteAEliminar.getDomicilio().getId());
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE);

                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                connection.commit();

                logger.info("Paciente " + id + " eliminado");
            }


        }catch (Exception e) {
            logger.error("No se ha podido eliminar el paciente", e);
            try{
                connection.rollback();
            } catch (SQLException ex){
                logger.error("No se ha podido ejecutar el rollback", ex);
            } finally {
                try{
                    connection.setAutoCommit(true);
                } catch (SQLException ex){
                    logger.error("No se ha podido desactivar el autocommit", ex);
                }
            }
        }finally {
            try{
                connection.close();
            } catch (SQLException ex){
                logger.error("No se ha podido cerrar la conexión", ex);
            }
        }
    }
}
