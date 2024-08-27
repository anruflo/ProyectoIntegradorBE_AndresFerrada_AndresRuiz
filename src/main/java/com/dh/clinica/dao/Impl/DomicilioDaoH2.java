package com.dh.clinica.dao.Impl;

import com.dh.clinica.dao.IDao;
import com.dh.clinica.db.H2Connection;
import com.dh.clinica.model.Domicilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class DomicilioDaoH2 implements IDao<Domicilio> {
    public static final Logger logger = LoggerFactory.getLogger(DomicilioDaoH2.class);

    public static final String INSERT = "INSERT INTO DOMICILIOS VALUES (DEFAULT, ?, ?, ?, ?)";
    public static final String SELECT_ID = "SELECT * FROM DOMICILIOS WHERE ID = ?";
    public static final String SELECT_ALL = "SELECT * FROM DOMICILIOS";
    public static final String UPDATE = "UPDATE DOMICILIOS SET CALLE=?, NUMERO=?, LOCALIDAD=?, PROVINCIA=? WHERE ID=?";
    public static final String DELETE = "DELETE FROM DOMICILIOS WHERE ID = ?";

    @Override
    public Domicilio registrar(Domicilio domicilio) {
        Connection connection = null;
        Domicilio domiclioARetornar = null;

       try{
           connection = H2Connection.getConnection();
           connection.setAutoCommit(false);

           PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

           preparedStatement.setString(1, domicilio.getCalle());
           preparedStatement.setInt(2, domicilio.getNumero());
           preparedStatement.setString(3, domicilio.getLocalidad());
           preparedStatement.setString(4, domicilio.getProvincia());

           preparedStatement.executeUpdate();
           connection.commit();

           ResultSet resultSet = preparedStatement.getGeneratedKeys();
           if(resultSet.next()){
               Integer id = resultSet.getInt(1);
               domiclioARetornar = new Domicilio(id, domicilio.getCalle(), domicilio.getNumero(), domicilio.getLocalidad(), domicilio.getProvincia());
           }
           logger.info("Domicilio guardado " + domiclioARetornar);

       } catch (Exception e){
           logger.error("No se ha podido guardar el domicilio", e);
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
         return domiclioARetornar;
    }


    @Override
    public Domicilio buscarPorId(Integer id) {
        Connection connection = null;
        Domicilio domicilioEncontrado = null;

        try{
            connection = H2Connection.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID);

           preparedStatement.setInt(1, id);

           ResultSet resultSet = preparedStatement.executeQuery();

           if(resultSet.next()){
               Integer idDb = resultSet.getInt(1);
               String calle = resultSet.getString(2);
               Integer numero = resultSet.getInt(3);
               String localidad = resultSet.getString(4);
               String provincia = resultSet.getString(5);

               domicilioEncontrado = new Domicilio(idDb, calle, numero, localidad, provincia);
           }

           if(domicilioEncontrado != null){
               logger.info("Domicilio encontrado " + domicilioEncontrado);
           } else {
               logger.info("Domicilio no encontrado");
           }

        } catch (Exception e){
            logger.error("No se ha podido encontrar el domicilio", e);

        } finally {
            try{
                connection.close();
            } catch (SQLException e){
                logger.error("No se ha podido cerrar la base de datos", e);
            }
        }


        return domicilioEncontrado;
    }

    @Override
    public List<Domicilio> buscarTodos() {
        Connection connection = null;
        List<Domicilio> domicilios = null;
        Domicilio domicilio = null;

        try{
           connection = H2Connection.getConnection();
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(SELECT_ALL);

           while(resultSet.next()) {
               Integer id = resultSet.getInt(1);
               String calle = resultSet.getString(2);
               Integer numero = resultSet.getInt(3);
               String localidad = resultSet.getString(4);
               String provincia = resultSet.getString(5);

               domicilio = new Domicilio(id, calle, numero, localidad, provincia);

               logger.info("Domicilio " + domicilio);
               domicilios.add(domicilio);
           }

        }catch(Exception e){
            logger.error("No se ha podido encontrar el domicilio", e);
        }finally {
            try{
                connection.close();
            } catch (SQLException ex){
                logger.error("No se ha podido cerrar la conexión", ex);
            }
        }
        return domicilios;
    }

    @Override
    public void actualizar(Domicilio domicilio) {
        Connection connection = null;

        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);


            preparedStatement.setString(1, domicilio.getCalle());
            preparedStatement.setInt(2, domicilio.getNumero());
            preparedStatement.setString(3,domicilio.getLocalidad());
            preparedStatement.setString(4,domicilio.getProvincia());
            preparedStatement.setInt(5, domicilio.getId());

            preparedStatement.executeUpdate();
            connection.commit();

            logger.info("Domicilio actualizado " + domicilio);

        }catch (Exception e){
            logger.info("No se ha podido actualizar el domicilio", e);
            try{
              connection.rollback();
            } catch(SQLException ex){
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
        try{
            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            logger.info("Domicilio " + id + " eliminado");

        }catch (Exception e){
            logger.error("No se ha podido eliminar el domicilio", e);
            try{
                connection.rollback();
            } catch(SQLException ex){
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
