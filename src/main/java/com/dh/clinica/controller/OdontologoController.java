package com.dh.clinica.controller;

import com.dh.clinica.entity.Odontologo;
import com.dh.clinica.service.Impl.OdontologoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Odontologo> guardarOdontologo(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<Odontologo> odontologo = odontologoService.buscarPorId(id);

        if (odontologo.isPresent()) {
            return ResponseEntity.ok(odontologo);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Odontologo>> listarTodos() {
        return ResponseEntity.ok(odontologoService.listarTodos());
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarOdontologo(@RequestBody Odontologo odontologo) {
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarPorId(odontologo.getId());

        if (odontologoEncontrado.isPresent()) {
            Odontologo odontologoActualizado = odontologoEncontrado.get();

            odontologoService.actualizarOdontologo(odontologo);

            String jsonResponse = "{\"mensaje\": \"Odontólogo actualizado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Integer id) {
            odontologoService.eliminarOdontologo(id);
            String jsonResponse = "{\"mensaje\": \"Odontólogo eliminado\"}";
            return ResponseEntity.ok(jsonResponse);

    }


    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<List<Odontologo>> buscarPorNumeroMatricula(@PathVariable Integer matricula) {
        return ResponseEntity.ok(odontologoService.buscarPorNumeroMatricula(matricula));
    }

    @GetMapping("/apellidoYNombre")
    public ResponseEntity<List<Odontologo>> buscarPorApellidoYNombre(@RequestParam String apellido, @RequestParam String nombre) {
        return ResponseEntity.ok(odontologoService.buscarPorApellidoYNombre(apellido, nombre));
    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Odontologo>> buscarPorApellido(@PathVariable String apellido) {
        return ResponseEntity.ok(odontologoService.buscarPorApellido(apellido));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Odontologo>> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(odontologoService.buscarPorNombre(nombre));
    }




}
