package med.voll.api.domain.medico;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//Classe que pega o objeto Medico e salva no banco de dados
//Repositório que recebe o objeto médico e o tipo do Id = Long
public interface MedicoRepository extends JpaRepository<Medico, Long> {

	Page<Medico> findAllByAtivoTrue(Pageable paginacao);

//	Selecione na tabela de médicos um médico que esteja ativo e que seja na mesma especialidade escolhida, trazendo aleatoriamente e livre na data
	@Query("""		
			SELECT m FROM medicos m
			WHERE m.ativo = 1
			     AND m.especialidade = :especialidade
			     AND m.id NOT IN (
            SELECT c.medico.id FROM Consulta c 
            WHERE c.data = :data and c.motivoCancelamento is null
			     )
			 ORDER BY FUNCTION('RAND')
			 limit 1
			""")
	Medico escolherMedicoAleatorioLivreData(Especialidade especialidade, LocalDateTime data);

	@Query("""		
			select m.ativo from medicos m where m.id = :idMedico
			""")
	Boolean findAtivoById(Number idMedico);

}
