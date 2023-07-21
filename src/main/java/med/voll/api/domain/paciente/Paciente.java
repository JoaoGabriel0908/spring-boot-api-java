package med.voll.api.domain.paciente;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "pacientes")
@Entity(name = "pacientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
	public Paciente(DadosCadastroPaciente dados) {
		this.setAtivo(true);
		this.nome = dados.nome();
		this.cpf = dados.cpf();
		this.email = dados.email();
		this.endereco = new Endereco(dados.endereco());
		this.telefone = dados.telefone();
	}
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private boolean ativo;
	private String nome;
	private String email;
	private String telefone;
	private String cpf;
//	Anotação Embedded, indica uma classe separada mas que fica na mesma tabela do Medico
	@Embedded
	private Endereco endereco;

	
	public Endereco getEndereco() {
		return endereco;
	}



	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public Paciente() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
//		Se os dados forem diferente de nulo, ou seja, se ele adicionar um dado em um campo
		if(dados.nome() != null) {
			this.nome = dados.nome();
		}
		if(dados.telefone() != null) {
			this.telefone = dados.telefone();
		}
		if(dados.endereco() != null) {
			this.endereco.atualizarInforamacoes(dados.endereco());
		}
		if(dados.email() != null) {
			this.email = dados.email();
		}
	}



	public boolean isAtivo() {
		return ativo;
	}



	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public void excluir() {
		this.ativo = false;
	}

	
}
