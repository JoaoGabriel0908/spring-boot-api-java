package med.voll.api.domain.consulta.validacoes;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsultas{
	
//	Validação recebendo os dados do agendamento e verificando se a consulta está no dentro do horario de funcionamento
	public void validar(DadosAgendamentoConsulta dados) {
		var dataConsulta = dados.data();
		var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
		var antesDaAberturaClinica = dataConsulta.getHour() < 7;
		var depoisDoEncerramentoClinica = dataConsulta.getHour() > 18;
		
		if(domingo || antesDaAberturaClinica || depoisDoEncerramentoClinica) {
			throw new ValidacaoException("Consulta fora do horário de funcionamento da clinica");
		}
	}
}		
