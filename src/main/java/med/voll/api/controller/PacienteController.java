package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.paciente.DatosListadoPaciente;
import med.voll.api.paciente.DatosRegistroPaciente;
import med.voll.api.paciente.Paciente;
import med.voll.api.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    public void registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datos){
        repository.save(new Paciente(datos));
    }

    @GetMapping
    public Page<DatosListadoPaciente> listarPaciente(@PageableDefault(page = 0, size = 10, sort = {"nombre"}) Pageable paginacion) {
        return repository.findAll(paginacion).map(DatosListadoPaciente::new);
    }
}
