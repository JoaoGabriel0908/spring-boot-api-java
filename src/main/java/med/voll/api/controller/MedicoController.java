package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.DadosCadastroMedicos;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

import java.util.*;

//Classe de controller Rest durante a inicialização do projeto
@RequestMapping("/medico")
@RestController
public class MedicoController {
	
//	Injetando automáticamente o repositório e instanciar o repositório dentro da classe
	@Autowired
	private MedicoRepository medicoRepository;
	
//	Colocando o método Post e Colocando a requisição no corpo do post
	@PostMapping
	@Transactional
	public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicos dados, UriComponentsBuilder uriBuilder) {
		var medico = new Medico(dados);
		medicoRepository.save(medico);
		
//		Constroi o endereço da API e recupera o id do médico que foi constuído
		var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		
//		Retornando o código e o corpo da requisição
		return ResponseEntity.created(uri).body(new DadosDetalhementoMedico(medico));
	}
	
//	Método de listagem por paginação
//	Trazendo listas com os dados do médicos e as paginações com a classe Page
	
//	Pageble é uma classe para trazer a paginação das páginas e a ordenação, pela PagebleDefault conseguimos deixar algo padrão para a requisição
//	Caso não passmos nada na URL
	@GetMapping
	public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size=10, sort = {"nome"}) Pageable paginacao) {
		var page = medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
		return ResponseEntity.ok(page);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity atualizar (@RequestBody @Valid DadosAtualizacaoMedico dados) {
		var medico = medicoRepository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);
		
		return ResponseEntity.ok(new DadosDetalhementoMedico(medico));
	}
	
//	Pegando o id pela URL
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity excluir (@PathVariable Long id) {
//		medicoRepository.deleteById(id);
		var medico = medicoRepository.getReferenceById(id);
		medico.excluir();
		
//		Retornando resposta para o método
		return ResponseEntity.noContent().build();
	}
	
//	Pegando o id pela URL
	@GetMapping("/{id}")
	public ResponseEntity detalhar (@PathVariable Long id) {
//		medicoRepository.deleteById(id);
		var medico = medicoRepository.getReferenceById(id);
		
//		Retornando resposta para o método
		return ResponseEntity.ok(new DadosDetalhementoMedico(medico));
	}
}
