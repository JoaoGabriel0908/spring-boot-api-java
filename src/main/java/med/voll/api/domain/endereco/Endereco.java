package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Classe JPA, classe responsável pela comunicação com o banco de dados
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

@Embeddable
public class Endereco {
	
	public Endereco() {}
	
	public Endereco(DadosEndereco dados) {
		this.setLogradouro(dados.logradouro());
		this.cep = dados.cep();
		this.cidade = dados.cidade();
		this.uf = dados.uf();
		this.bairro = dados.bairro();
		this.numero = dados.numero();
		this.complemento = dados.complemento();
		
	}
	private String logradouro;
	private String bairro;
	private String cep;
	private Number numero;
	private String complemento;
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Number getNumero() {
		return numero;
	}

	public void setNumero(Number numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	private String uf;
	private String cidade;
	
	public void atualizarInforamacoes(DadosEndereco dados) {
		if(dados.logradouro() != null) {
			this.logradouro = dados.logradouro();
		}
		
		if(dados.bairro() != null) {
			this.bairro = dados.bairro();
		}
		
		if(dados.cep() != null) {
			this.cep = dados.cep();
		}
		
		if(dados.numero() != null) {
			this.numero = dados.numero();
		}
		
		if(dados.complemento() != null) {
			this.complemento = dados.complemento();
		}
		
		if(dados.uf() != null) {
			this.uf = dados.uf();
		}
		
		if(dados.cidade() != null) {
			this.cidade = dados.cidade();
		}
		
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
}
