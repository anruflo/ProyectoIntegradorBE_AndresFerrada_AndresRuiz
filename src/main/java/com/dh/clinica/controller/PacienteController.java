package com.dh.clinica.controller;

import com.dh.clinica.entity.Domicilio;
import com.dh.clinica.entity.Paciente;
import com.dh.clinica.service.Impl.PacienteService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Optional<Paciente> paciente = pacienteService.buscarPorId(id);

        if(paciente.isPresent()){
            return ResponseEntity.ok(paciente.get());
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Paciente>> buscarTodos(){
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarPaciente(@RequestBody Paciente paciente) {
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPorId(paciente.getId());

        if (pacienteEncontrado.isPresent()) {
            Paciente pacienteActualizado = pacienteEncontrado.get();

            pacienteService.actualizarPaciente(paciente);

            String jsonResponse = "{\"mensaje\": \"Paciente actualizado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Integer id){
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("{\"mensaje\": \"Paciente eliminado\"}");
    }

    @GetMapping("/buscarApellidoNombre")
    public ResponseEntity<List<Paciente>> buscarApellidoYNombre(@RequestParam String apellido, @RequestParam String nombre){
        return ResponseEntity.ok(pacienteService.buscarPorApellidoYNombre(apellido, nombre));
    }

    @GetMapping("/buscarNombre/{nombre}")
    public ResponseEntity<List<Paciente>> buscarNombreLike(@PathVariable String nombre){
        return ResponseEntity.ok(pacienteService.buscarLikeNombre(nombre));
    }
}
