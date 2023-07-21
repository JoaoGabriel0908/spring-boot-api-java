package med.voll.api.controller;

import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

public record DadosDetalhementoPaciente(Long id, String nome, String cpf, String telefone, String email, Endereco endereco) {
	
	public DadosDetalhementoPaciente(Paciente paciente) {
		this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), paciente.getEndereco());
	}
	
	
}
