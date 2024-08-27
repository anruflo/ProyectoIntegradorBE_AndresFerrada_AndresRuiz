package com.dh.clinica.controller;

import com.dh.clinica.model.Odontologo;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.service.OdontologoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Odontologo> guardarOdontologo(@RequestBody Odontologo odontologo){
        return  ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id){
        Odontologo odontologo = odontologoService.buscarPorId(id);

        if(odontologo != null){
            return ResponseEntity.ok(odontologo);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Odontologo>> listarTodos(){
        return ResponseEntity.ok(odontologoService.listarTodos());
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarOdontologo(@RequestBody Odontologo odontologo){
        Odontologo odontologoEncontrado = odontologoService.buscarPorId(odontologo.getId());

        if(odontologoEncontrado != null){
            odontologoService.actualizarOdontologo(odontologo);
            String jsonResponse = "{\"mensaje\": \"Odontólogo actualizado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Integer id){
        Odontologo odontologEncontrado = odontologoService.buscarPorId(id);

        if(odontologEncontrado != null){
            odontologoService.eliminarOdontologo(id);
            String jsonResponse = "{\"mensaje\": \"Odontólogo eliminado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else{
            return ResponseEntity.status(404).build();
        }
    }

}
