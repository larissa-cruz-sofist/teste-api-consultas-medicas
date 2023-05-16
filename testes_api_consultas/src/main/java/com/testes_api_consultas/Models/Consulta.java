package com.testes_api_consultas.Models;

public class Consulta {

    public int idPaciente;
    public String especialidade;
    public String data;

    public Consulta() {

    }

    public Consulta criarConsultaEspecialidadeDefinida() {

        this.idPaciente = 66;
        this.especialidade = EspecialidadeMedico.ORTOPEDIA.toString();
        this.data = "2023-09-16T16:30";

        return this;
    }
    
}
