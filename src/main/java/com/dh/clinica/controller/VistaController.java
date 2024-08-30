package com.dh.clinica.controller;

import com.dh.clinica.model.Odontologo;
import com.dh.clinica.model.Paciente;
import com.dh.clinica.service.OdontologoService;
import com.dh.clinica.service.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VistaController {
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    public VistaController(PacienteService pacienteService , OdontologoService odontologoService) {
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    // localhost:8080/20  -> @PathVariable
    // localhost:8080?id=1  -> @RequestParams
    @GetMapping("/index")
    public String mostrarPacientePorId(Model model, @RequestParam Integer id){
        Paciente paciente = pacienteService.buscarPorId(id);
        model.addAttribute("nombre", paciente.getNombre());
        model.addAttribute("apellido", paciente.getApellido());
        return "paciente";
    }

    @GetMapping("/index2/{id}")
    public String mostrarPacientePorId2(Model model, @PathVariable Integer id){
        Paciente paciente = pacienteService.buscarPorId(id);
        model.addAttribute("nombre", paciente.getNombre());
        model.addAttribute("apellido", paciente.getApellido());
        return "paciente";
    }





    // localhost:8080/20  -> @PathVariable
    // localhost:8080?id=1  -> @RequestParams
    @GetMapping("/index3")
    public String mostrarOdontologoPorId(Model model, @RequestParam Integer id){
        Odontologo odontologo = odontologoService.buscarPorId(id);
        model.addAttribute("nombre", odontologo.getNombre());
        model.addAttribute("apellido", odontologo.getApellido());
        return "odontólogo";
    }

    @GetMapping("/index3/{id}")
    public String mostrarOdontologoPorId2(Model model, @PathVariable Integer id){
        Odontologo odontologo = odontologoService.buscarPorId(id);
        model.addAttribute("nombre", odontologo.getNombre());
        model.addAttribute("apellido", odontologo.getApellido());
        return "odontólogo";
    }
}
