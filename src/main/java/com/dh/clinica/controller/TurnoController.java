package com.dh.clinica.controller;

import com.dh.clinica.entity.Turno;
import com.dh.clinica.service.Impl.TurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarTurno(@RequestBody Turno turno) {
        return ResponseEntity.ok(turnoService.guardarTurno(turno));
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Turno>> buscarTodos() {
        return ResponseEntity.ok(turnoService.listarTodos());
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<Turno> turno = turnoService.buscarPorId(id);
        return ResponseEntity.ok(turno.get());
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarTurno(@RequestBody Turno turno) {
        turnoService.actualizarTurno(turno);
        return ResponseEntity.ok("{\"mensaje\": \"Turno actualizado\"}");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Integer id) {
        turnoService.eliminarTurno(id);

        return ResponseEntity.ok("{\"mensaje\": \"Turno Eliminado\"}");
    }

    @GetMapping("/lista/{apellido}")
    public ResponseEntity<List<Turno>> buscarTurnoPorApellidoPaciente(@PathVariable String apellido) {
        return ResponseEntity.ok(turnoService.buscarTurnoPaciente(apellido));
    }
}
