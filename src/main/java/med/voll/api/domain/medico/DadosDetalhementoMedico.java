package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.Endereco;

public record DadosDetalhementoMedico(Number id, String nome, String telefone, String email, String crm, Especialidade especialidade, Endereco endereco) {
	
	public  DadosDetalhementoMedico(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
	}
	
	
}
