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

            pacienteActualizado.setApellido(paciente.getApellido());
            pacienteActualizado.setNombre(paciente.getNombre());
            pacienteActualizado.setDni(paciente.getDni());
            pacienteActualizado.setFechaIngreso(paciente.getFechaIngreso());

            if (paciente.getDomicilio() != null) {
                Domicilio domicilioActualizado = pacienteActualizado.getDomicilio();

                domicilioActualizado.setCalle(paciente.getDomicilio().getCalle());
                domicilioActualizado.setNumero(paciente.getDomicilio().getNumero());
                domicilioActualizado.setLocalidad(paciente.getDomicilio().getLocalidad());
                domicilioActualizado.setProvincia(paciente.getDomicilio().getProvincia());

                pacienteActualizado.setDomicilio(domicilioActualizado);
            }

            pacienteService.actualizarPaciente(pacienteActualizado);

            String jsonResponse = "{\"mensaje\": \"Paciente actualizado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Integer id){
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarPorId(id);

        if(pacienteEncontrado.isPresent()){
            pacienteService.eliminarPaciente(id);
            String jsonResponse = "{\"mensaje\": \"Paciente eliminado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarApellidoNombre")
    public ResponseEntity<List<Paciente>> buscarApellidoYNombre(@RequestParam String apellido, @RequestParam String nombre){
        return ResponseEntity.ok(pacienteService.buscarPorApellidoYNombre(apellido, nombre));
    }
}
