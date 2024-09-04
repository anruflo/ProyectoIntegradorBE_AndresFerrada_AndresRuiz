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

            odontologoActualizado.setMatricula(odontologo.getMatricula());
            odontologoActualizado.setNombre(odontologo.getNombre());
            odontologoActualizado.setApellido(odontologo.getApellido());

            odontologoService.actualizarOdontologo(odontologoActualizado);

            String jsonResponse = "{\"mensaje\": \"Odontólogo actualizado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Integer id) {
        Optional<Odontologo> odontologEncontrado = odontologoService.buscarPorId(id);

        if (odontologEncontrado.isPresent()) {
            odontologoService.eliminarOdontologo(id);
            String jsonResponse = "{\"mensaje\": \"Odontólogo eliminado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

}
