package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsultas;
import med.voll.api.domain.consulta.RegrasAgendaDeConsultas;
import med.voll.api.domain.medico.DadosDetalhementoMedico;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.MedicoRepository;

//Teste unitária para testar apenas o controller
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ConsultaControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
//	Json que chega para a API
	@Autowired
	private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsulta;
	
//	Json que volda da API
	@Autowired
	private JacksonTester<DadosDetalhamentoConsultas> dadosDetalhamentoConsulta;
	
	@MockBean
	private RegrasAgendaDeConsultas agendaConsultas;
	
//	Método para voltar o código HTTP 400
	
//	Chamando o método static post do método agendar
	@Test
	@DisplayName("Deveria voltar o código 400 quando as informações são inválidas")
	@WithMockUser
	void agendarCenario1() throws Exception {
		
//		Dispara uma requisição para agendar no método POST
		var response = mvc.perform(MockMvcRequestBuilders.post("/consultas")).andReturn().getResponse();
		
//		Verifica se o status da response é igual a 400
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("Deveria voltar o código 200 com as informações estão válidas")
	@WithMockUser
	void agendarCenario2() throws Exception {
		
		var data = LocalDateTime.now().plusHours(12);
		var especialidade = Especialidade.ORTOPEDIA;
		
		var dadosDetalhamento = new DadosDetalhamentoConsultas(null, 8, 8, data);
		
//		Quando o método agendar for chamado devolva um DadosDetalhamentoMedico
		when(agendaConsultas.agendar(any())).thenReturn(dadosDetalhamento);
		
		Long idMedico = (long) 8;
		Long idPaciente = (long) 8;
//		Dispara uma requisição para agendar no método POST
		var response = mvc.perform(
				MockMvcRequestBuilders
				.post("/consultas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dadosAgendamentoConsulta.write(new DadosAgendamentoConsulta(idMedico, idPaciente, data, especialidade)).getJson()
						)
				)
				.andReturn()
				.getResponse();
		
//		Verifica se o status da response é igual a 400
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		var jsonEsperado = dadosDetalhamentoConsulta.write(dadosDetalhamento).getJson();
		
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
	}
}
