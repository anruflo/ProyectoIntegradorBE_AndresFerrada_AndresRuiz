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
        Turno turnoAGuardar = turnoService.guardarTurno(turno);

        if(turnoAGuardar != null){
            return ResponseEntity.ok(turnoAGuardar);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Paciente u odontólogo no fueron encontrados");
        }
    }
    @GetMapping("/lista")
    public ResponseEntity<List<Turno>> buscarTodos(){
        return ResponseEntity.ok(turnoService.listarTodos());
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<Turno> turno = turnoService.buscarPorId(id);

        if (turno.isPresent()) {
            return ResponseEntity.ok(turno.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turno no encontrado");
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarTurno(@RequestBody Turno turno) {
        turnoService.actualizarTurno(turno);
        return ResponseEntity.ok("Turno actualizado con éxito");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Integer id) {
        Optional<Turno> turno = turnoService.buscarPorId(id);

        if(turno.isPresent()){
            turnoService.eliminarTurno(id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turno no encontrado");
        }

        return ResponseEntity.ok("Turno eliminado con éxito");
    }
}
