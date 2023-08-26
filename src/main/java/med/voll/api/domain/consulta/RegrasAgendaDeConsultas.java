package med.voll.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsultas;
import med.voll.api.domain.consulta.validacoes.ValidarCancelamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;

//Classe responsável por fazer a regra de agendamento de consultas é um serviço
@Service
public class RegrasAgendaDeConsultas {
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired(required = true)
	private java.util.List<ValidarCancelamentoDeConsulta> validadoresCancelamento;
	
//	Automaticamente o spring vai detectar que estou injetando uma lista de uma 
//	interface que implementa todas as classes que implementar e adiciona nessa lista
	@Autowired
	private java.util.List<ValidadorAgendamentoDeConsultas> validators;
	
	public DadosDetalhamentoConsultas agendar(DadosAgendamentoConsulta dados) {
		
		if(!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoException("ID do paciente informado não existe");
		}
		
//		Se está vindo um id do médico e existe no banco de dados
		if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
			throw new ValidacaoException("ID do médico informado não existe");
		}
		
//		Percorre a lista de validadores implementando a função validar, passando os dados
		validators.forEach(v -> v.validar(dados));
		
//		Retorna um id do paciente
		Paciente paciente = pacienteRepository.getReferenceById(dados.idPaciente());
		Medico medico = escolherMedico(dados);
		
		if(medico == null) {
			throw new ValidacaoException("Não existe médico disponivel nessa data");
		}
		
		Consulta consulta = new Consulta(null, medico, paciente, dados.data(), null);
		consultaRepository.save(consulta);
		
		return new DadosDetalhamentoConsultas(consulta);
	}
	
	public void cancelar(DadosCancelamentoConsulta dados) {
		
		if(!consultaRepository.existsById(dados.idConsulta())) {
			throw new ValidacaoException("ID da consulta informado não existe");
		}
		
		validadoresCancelamento.forEach(v -> v.validar(dados));
		
		var consulta = consultaRepository.getReferenceById(dados.idConsulta());
		consulta.cancelar(dados.motivo());
	}

		
		
		
//	Escolha do médico caso ele escolha ou caso ele não escolha, então vem aleatoriamente
	private Medico escolherMedico(DadosAgendamentoConsulta dados) {
		if(dados.idMedico() != null) {
			return medicoRepository.getReferenceById(dados.idMedico());
		}
		
		if(dados.especialidade() == null) {
			throw new ValidacaoException("Especialidade é obrigatória quando não há médicos escolhidos");
		}
		
		return medicoRepository.escolherMedicoAleatorioLivreData(dados.especialidade(), dados.data());
		
	}
}
