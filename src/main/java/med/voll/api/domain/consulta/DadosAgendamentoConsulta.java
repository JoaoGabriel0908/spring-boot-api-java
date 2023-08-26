package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

//Classe que vai receber os dados para agendar uma consulta
//Os nomes enviados na API devem ser idênticos ao da classe, pois o Spring consegue preencher corretamente
public record DadosAgendamentoConsulta(
		
		@JsonAlias("medico_id")
		Long idMedico,
		
		@NotNull @JsonAlias({"paciente_id", "id_paciente"}) 
		Long idPaciente,
		
		@NotNull @Future @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
		LocalDateTime data,
		
		Especialidade especialidade
		
		) {
	
}
