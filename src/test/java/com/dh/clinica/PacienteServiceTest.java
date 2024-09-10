package com.dh.clinica;

import com.dh.clinica.entity.Domicilio;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.service.Impl.PacienteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class PacienteServiceTest {
    @Autowired
    PacienteService pacienteService;
    Paciente paciente;
    Paciente pacienteDesdeDb;

    @BeforeEach
    void crearPaciente(){
        Domicilio domicilio = new Domicilio(null, "Falsa", 123, "San Pedro", "Concepción");
        paciente = new Paciente();
        paciente.setApellido("Perez");
        paciente.setNombre("Juan");
        paciente.setDni("666777");
        paciente.setFechaIngreso(LocalDate.of(2024, 9, 8));
        paciente.setDomicilio(domicilio);
        pacienteDesdeDb = pacienteService.guardarPaciente(paciente);
    }


    @Test
    @DisplayName("Testear que Paciente se guarde en la base de datos con domicilio")
    void testInsertarPaciente(){
        assertNotNull(pacienteDesdeDb.getId());
        assertTrue(pacienteDesdeDb.getId() > 0);
    }

    @Test
    @DisplayName("Testear que un paciente pueda ser obtenido por su ID")
    void testBuscarPorId() {
        Integer id = pacienteDesdeDb.getId();
        Paciente pacienteEncontrado = pacienteService.buscarPorId(id).get();
        assertEquals(id, pacienteEncontrado.getId());
    }
}


