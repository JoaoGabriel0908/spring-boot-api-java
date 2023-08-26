package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

//Retorna os detalhes de uma consulta
public record DadosDetalhamentoConsultas(
		Number id, Number idMedico, Number idPaciente, LocalDateTime data
		) {

	public DadosDetalhamentoConsultas(Consulta consulta) {
		this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
	}

}
