package med.voll.api.domain.medico;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

//Classe JPA, classe responsável pela comunicação com o banco de dados
//Nome da tabela e o nome da entidade
@Table(name = "medicos")
@Entity(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
	
public Medico(DadosCadastroMedicos dados) {
		this.setAtivo(true);
		this.setNome(dados.nome());
		this.setEmail(dados.email());
		this.setCrm(dados.crm());
		this.setTelefone(dados.telefone());
		this.especialidade = dados.especialidade();
		this.endereco = (new Endereco(dados.endereco()));
	}

public Endereco getEndereco() {
	return endereco;
}

public void setEndereco(Endereco endereco) {
	this.endereco = endereco;
}

public Medico () {
	
}

//Gerando id das tabelas
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
private String nome;
private String email;
private String crm;
private String telefone;
private boolean ativo;
//	Anotação Embedded, indica uma classe separada mas que fica na mesma tabela do Medico
@Embedded
private Endereco endereco;

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

//Definindo enum
@Enumerated(EnumType.STRING)
private Especialidade especialidade;

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

	public String getCrm() {
	return crm;
}

public void setCrm(String crm) {
	this.crm = crm;
}

	public String getTelefone() {
	return telefone;
}

public void setTelefone(String telefone) {
	this.telefone = telefone;
}
	
public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	
	public void atualizarInformacoes (DadosAtualizacaoMedico dados) {
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


