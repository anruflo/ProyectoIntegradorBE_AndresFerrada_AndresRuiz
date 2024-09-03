package com.dh.clinica;
/*

import com.dh.clinica.dao.Impl.OdontologoDaoH2;
import com.dh.clinica.db.H2Connection;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.service.OdontologoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OdontologoServiceTest {

    static final Logger logger = LoggerFactory.getLogger(OdontologoServiceTest.class);
    OdontologoService odontologoService = new OdontologoService(new OdontologoDaoH2());

    @BeforeAll
    static void tablas(){
        H2Connection.crearTablas();
    }

    @Test
    @DisplayName("Testear que Odontólogo se ha guardado en base de datos")

    void testInsertarDatos(){
        Odontologo odontologo = new Odontologo(666, "María", "Perez");
        Odontologo odontologoGuardadoDB = odontologoService.guardarOdontologo(odontologo);

        assertNotNull(odontologoGuardadoDB.getId());
        assertTrue(odontologoGuardadoDB.getId() > 0);
        assertEquals(3, odontologoGuardadoDB.getId());
    }

    @Test
    @DisplayName("Testear enlistado de todos los elementos")

    void testListarTodos(){
        List<Odontologo> odontologos = odontologoService.listarTodos();

        assertFalse(odontologos.isEmpty());

    }

}*/
