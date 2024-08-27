package com.dh.clinica.controller;

import com.dh.clinica.model.Paciente;
import com.dh.clinica.service.PacienteService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService){
        this.pacienteService = pacienteService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente){
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }


    @GetMapping("/buscar/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Integer id){
        Paciente paciente = pacienteService.buscarPorId(id);

        if(paciente != null){
            return ResponseEntity.ok(paciente);
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Paciente>> buscarTodos(){
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarPaciente(@RequestBody Paciente paciente){
        Paciente pacienteEncontrado = pacienteService.buscarPorId(paciente.getId());

        if(pacienteEncontrado != null){
        pacienteService.actualizarPaciente(paciente);
        String jsonResponse = "{\"mensaje\": \"Paciente actualizado\"}";
        return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Integer id){
        Paciente pacienteEncontrado = pacienteService.buscarPorId(id);

        if(pacienteEncontrado == null){
            pacienteService.eliminarPaciente(id);
            String jsonResponse = "{\"mensaje\": \"Paciente eliminado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

}
