package com.dh.clinica.controller;

import com.dh.clinica.entity.Turno;
import com.dh.clinica.service.Impl.TurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Completar las operaciones que faltan


}
