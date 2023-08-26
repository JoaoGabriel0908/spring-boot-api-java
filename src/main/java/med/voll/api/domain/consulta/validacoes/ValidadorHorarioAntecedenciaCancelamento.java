package med.voll.api.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedenciaCancelamento implements ValidarCancelamentoDeConsulta{
	
	@Autowired
	private ConsultaRepository repository;
	
	@Override
	public void validar(DadosCancelamentoConsulta dados) {
		var consulta = repository.getReferenceById(dados.idConsulta());
		var agora = LocalDateTime.now();
		var diferencaDeMinutos = Duration.between(agora, consulta.getData()).toHours();
		
		if(diferencaDeMinutos < 24) {
			throw new ValidacaoException("A consulta deve ser agendada com antecedência de 30 minutos");
		}
	}
}
