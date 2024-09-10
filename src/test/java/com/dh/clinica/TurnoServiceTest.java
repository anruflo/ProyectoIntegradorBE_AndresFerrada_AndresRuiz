package com.dh.clinica;

import com.dh.clinica.entity.Domicilio;
import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.entity.Turno;
import com.dh.clinica.service.Impl.OdontologoService;
import com.dh.clinica.service.Impl.PacienteService;
import com.dh.clinica.service.Impl.TurnoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class TurnoServiceTest {
    @Autowired
    TurnoService turnoService;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    OdontologoService odontologoService;

    Domicilio domicilio;
    Paciente paciente;
    Paciente pacienteDesdeDb;
    Odontologo odontologo;
    Odontologo odontologoDesdeDb;
    Turno turno;
    Turno turnoDesdeDb;

    @BeforeEach
    void crearEntidades(){
            // Crear domicilio
            domicilio = new Domicilio(null, "Falsa", 123, "San Pedro", "Concepción");

            // Crear paciente
            paciente = new Paciente();
            paciente.setApellido("Perez");
            paciente.setNombre("Juan");
            paciente.setDni("666777");
            paciente.setFechaIngreso(LocalDate.of(2024, 9, 2));
            paciente.setDomicilio(domicilio);
            pacienteDesdeDb = pacienteService.guardarPaciente(paciente);

            // Crear odontólogo
            odontologo = new Odontologo();
            odontologo.setMatricula(123);
            odontologo.setNombre("Juan");
            odontologo.setApellido("Perez");
            odontologoDesdeDb = odontologoService.guardarOdontologo(odontologo);

            // Crear turno
            turno = new Turno();
            turno.setPaciente(pacienteDesdeDb);
            turno.setOdontologo(odontologoDesdeDb);
            turno.setFecha(LocalDate.of(2024, 10, 8));
            turnoDesdeDb = turnoService.guardarTurno(turno);
    }


    @Test
    @DisplayName("Testear que Turno se guarde en la base de datos")
    void testInsertarTurno(){
        assertNotNull(turnoDesdeDb.getId());
        assertTrue(turnoDesdeDb.getId() > 0);
    }

    @Test
    @DisplayName("Testear el listado de todos los elementos")
    void testListarTodos(){
        List<Turno> turnos = turnoService.listarTodos();
        assertFalse(turnos.isEmpty());
    }
}