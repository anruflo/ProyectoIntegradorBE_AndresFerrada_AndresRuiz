package com.dh.clinica;


import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.service.Impl.OdontologoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class OdontologoServiceTest {

    @Autowired
    OdontologoService odontologoService;
    Odontologo odontologo;
    Odontologo odontologoDesdeDb;


    @BeforeEach
    void crearOdontologo(){
        odontologo = new Odontologo();
        odontologo.setMatricula(123);
        odontologo.setNombre("Juan");
        odontologo.setApellido("Perez");
        odontologoDesdeDb = odontologoService.guardarOdontologo(odontologo);
    }


    @Test
    @DisplayName("Testear que Odontólogo se guarde en la base de datos")

    void testInsertarDatos(){
        assertNotNull(odontologoDesdeDb.getId());
        assertTrue(odontologoDesdeDb.getId() > 0);
        assertEquals(1, odontologoDesdeDb.getId());
    }

    @Test
    @DisplayName("Testear el listado de todos los elementos")

    void testListarTodos(){
        List<Odontologo> odontologos = odontologoService.listarTodos();

        assertFalse(odontologos.isEmpty());

    }

}
