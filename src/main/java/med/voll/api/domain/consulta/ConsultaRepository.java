package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{

	Boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Number idMedico, LocalDateTime data);

	Boolean existsByPacienteIdAndDataBetween(Number idPaciente, LocalDateTime primeiroHorario,
			LocalDateTime ultimoHorario);

}
