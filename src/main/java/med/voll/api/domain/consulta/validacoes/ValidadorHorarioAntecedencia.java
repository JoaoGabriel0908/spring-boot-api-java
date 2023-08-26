package med.voll.api.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

@Component("ValidadorHorarioAntecedenciaAgendamento")
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsultas{
	
	@Autowired
	private ConsultaRepository repository;
	
	@Override
	public void validar(DadosAgendamentoConsulta dados) {
		var dataConsulta = dados.data();
		var agora = LocalDateTime.now();
		var diferencaDeMinutos = Duration.between(agora, dataConsulta).toMinutes();
		
		if(diferencaDeMinutos < 30) {
			throw new ValidacaoException("A consulta deve ser agendada com antecedência de 30 minutos");
		}
	}
}
