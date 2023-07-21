package med.voll.api.controller;

import java.awt.print.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.DadosAtualizacaoPaciente;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.DadosListagemPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhementoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder)
	{
		var paciente = new Paciente(dados);
		pacienteRepository.save(paciente);
		
//		Constroi o endereço da API e recupera o id do médico que foi constuído
		var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
		
//		Retornando o código e o corpo da requisição
		return ResponseEntity.created(uri).body(new DadosDetalhementoPaciente(paciente));
	}
	
	@GetMapping
	public ResponseEntity<Page<DadosListagemPaciente>> listar(org.springframework.data.domain.Pageable paginacao) {
		var page = pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
		return ResponseEntity.ok(page);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhementoPaciente> atualizar(@RequestBody @Valid  DadosAtualizacaoPaciente dados) {
		var paciente = pacienteRepository.getReferenceById(dados.id());
		paciente.atualizarInformacoes(dados);
		
		return ResponseEntity.ok(new DadosDetalhementoPaciente(paciente));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhementoPaciente> excluir(@PathVariable Long id) {
//		medicoRepository.deleteById(id);
		var paciente = pacienteRepository.getReferenceById(id);

		return ResponseEntity.ok(new DadosDetalhementoPaciente(paciente));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhementoPaciente> detalhar(@PathVariable Long id) {
//		medicoRepository.deleteById(id);
		var paciente = pacienteRepository.getReferenceById(id);

		return ResponseEntity.ok(new DadosDetalhementoPaciente(paciente));
	}
}
