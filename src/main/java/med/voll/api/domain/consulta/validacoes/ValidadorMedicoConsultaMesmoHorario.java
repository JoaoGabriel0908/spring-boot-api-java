package med.voll.api.domain.consulta.validacoes;

import java.time.DayOfWeek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;

@Component
public class ValidadorMedicoConsultaMesmoHorario implements ValidadorAgendamentoDeConsultas{
	
	@Autowired
	private ConsultaRepository repository;
	
	public void validar(DadosAgendamentoConsulta dados) {
		boolean medicoPossuiOutraConsultaNoHorario = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
		if(medicoPossuiOutraConsultaNoHorario) {
			throw new ValidacaoException("Médico já possui uma consulta nesse horário");
		}
	}
}
