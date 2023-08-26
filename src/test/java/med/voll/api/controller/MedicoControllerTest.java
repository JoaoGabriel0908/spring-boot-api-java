package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.DadosCadastroMedicos;
import med.voll.api.domain.medico.DadosDetalhementoMedico;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class MedicoControllerTest {

		@Autowired
		private MockMvc mvc;
		
//		Json que chega para a API
		@Autowired
		private JacksonTester<DadosCadastroMedicos> dadosCadastroMedico;
		
//		Json que volda da API	
		@Autowired
		private JacksonTester<DadosDetalhementoMedico> dadosDetalhementoMedico;
		
		@MockBean
		private MedicoRepository repository;
		
//		Método para voltar o código HTTP 400
//		Chamando o método static post do método agendar
		@Test
		@DisplayName("Deveria voltar o código 400 quando as informações são inválidas")
		@WithMockUser
		void cadastrarMedicoCenario1() throws Exception {
			
//			Dispara uma requisição para agendar no método POST
			var response = mvc.perform(MockMvcRequestBuilders.post("/medico")).andReturn().getResponse();
			
//			Verifica se o status da response é igual a 400
			assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		}
		
		
		@Test
		@DisplayName("Deveria voltar o código 200 com as informações estão válidas")
		@WithMockUser
		void cadastrarMedicoCenario2() throws Exception {
			
			var dadosCadastro = new DadosCadastroMedicos("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA, "61999999999", dadosEndereco());
			
			when(repository.save(any())).thenReturn(new Medico(dadosCadastro));
			
			var response = mvc.perform(
					MockMvcRequestBuilders
					.post("/medico")
					.contentType(MediaType.APPLICATION_JSON)
					.content(dadosCadastroMedico.write(dadosCadastro).getJson())
					)
					.andReturn()
					.getResponse();
			
			var dadosDetalhamento = new DadosDetalhementoMedico(0, dadosCadastro.nome(), dadosCadastro.email(), dadosCadastro.crm(), dadosCadastro.telefone(), dadosCadastro.especialidade(), new Endereco(dadosCadastro.endereco()));
			
			var jsonEsperado = dadosDetalhementoMedico.write(dadosDetalhamento).getJson();
			
			assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
			assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
		}
		
		private DadosEndereco dadosEndereco() {
			return new DadosEndereco("cidade", "logradouro", "bairro", "06604578", "SP", null, null);
			
		};
	
}
