package com.testes_api_consultas.Models;

import java.time.LocalDateTime;


public class Consulta {

    public String idPaciente;
    public String especialidade;
    public String data;
    public String idMedico;

    public Consulta() {

    }

    public Consulta criarConsultaEspecialidadeDefinida(String idPaciente) {

        LocalDateTime dataEHora = LocalDateTime.now().plusHours(1);

        this.idPaciente = idPaciente;
        this.especialidade = EspecialidadeMedico.DERMATOLOGIA.toString();
        this.data = dataEHora.toString();

        return this;
    }

    public Consulta criarConsultaMedicoDefinido(String idPaciente, String idMedico) {

        LocalDateTime dataEHora = LocalDateTime.now().plusHours(1);

        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.data = dataEHora.toString();

        return this;


    }
    
}
