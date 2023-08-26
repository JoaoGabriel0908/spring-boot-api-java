package med.voll.api.domain.medico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;

//Classe de teste que configura o mesmo banco de dados para o desenvolvimento com o teste
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTest {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private TestEntityManager em;
	
	@Test
	@DisplayName("Deveria devolver null quando o medico cadastrado não está disponivel na data")
	void escolherMedicoAleatorioLivreDataCenario1() {
//		Given ou arrange
		var proximaSegundaAs10 = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10, 0);
		
		var medico = cadastrarMedico("Medico", "medico@voll.com", "123456", Especialidade.CARDIOLOGIA);
		var paciente = cadastrarPaciente("Paciente", "pacein@.com", "05555");
		cadastrarConsulta(medico, paciente, proximaSegundaAs10);
		
//		When ou act
		var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
		
//		Then ou assert
		assertThat(medicoLivre).isNull();
	}
	
	@Test
	@DisplayName("Deveria devolver médico quando ele estiver disponivel na data")
	void escolherMedicoAleatorioLivreDataCenario2() {
//		Given ou arrange
		var proximaSegundaAs10 = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10, 0);
		
		var medico = cadastrarMedico("Medico", "medico@voll.com", "123456", Especialidade.CARDIOLOGIA);
		
//		When ou act
		var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
		
//		Then ou assert
		assertThat(medicoLivre).isEqualTo(medico);
	}
	
	
	private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
	    em.persist(new Consulta(null, medico, paciente, data, null));
	}

	private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
	    var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
	    em.persist(medico);
	    return medico;
	}

	private Paciente cadastrarPaciente(String nome, String email, String cpf) {
	    var paciente = new Paciente(dadosPaciente(nome, email, cpf));
	    em.persist(paciente);
	    return paciente;
	}

	private DadosCadastroMedicos dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
	    return new DadosCadastroMedicos(
	            nome,
	            email,
	            crm,
	            especialidade,
	            "61999999999",
	            dadosEndereco()
	    );
	}

	private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
	    return new DadosCadastroPaciente(
	            nome,
	            email,
	            "61999999999",
	            cpf,
	            dadosEndereco()
	    );
	}

	private DadosEndereco dadosEndereco() {
	    return new DadosEndereco(
	            "rua xpto",
	            "bairro",
	            "00000000",
	            "Brasilia",
	            "DF",
	            null,
	            null
	    );
	}

}
