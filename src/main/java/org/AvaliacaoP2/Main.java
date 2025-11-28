package org.AvaliacaoP2;

import org.AvaliacaoP2.dao.PacienteDAO;
import org.AvaliacaoP2.model.Paciente;
import org.AvaliacaoP2.utils.HibernateUtil;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {


        PacienteDAO dao = new PacienteDAO();

        System.out.println("------[ Create Paciente ] -------");
        Paciente paciente = new Paciente();
        paciente.setNome("DANIEL C. SILVA");
        paciente.setCpf("17317");
        paciente.setTelefone("99999-9999");
        paciente.setDataNascimento(LocalDate.of(1990, 1, 1));

        dao.salvarPaciente(paciente);

        System.out.println("");
        System.out.println("------[ Buscar por ID do Paciente ] -------");
        Paciente pacienteAux = dao.buscarPacientePorId(1);
        System.out.println(pacienteAux);

        System.out.println("");
        System.out.println("------[ Update Paciente ] -------");
        if (pacienteAux != null) {
            pacienteAux.setTelefone("88888-8888");
            pacienteAux.setNome("Miguel C. SILVA");
            dao.atualizarPaciente(pacienteAux);
            System.out.println("Atualizado: " + dao.buscarPacientePorId(1));
        }

        System.out.println("");
        System.out.println("------[ Listar todos Pacientes ] -------");
        dao.buscarPacientes().forEach(System.out::println);

        System.out.println("");
        System.out.println("------[ Deletar Paciente ] -------");
        if (dao.buscarPacientePorId(1) != null) {
            dao.deletarPaciente(1);
        }

        //HibernateUtil.shutdown();
    }
}
