package org.AvaliacaoP2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import org.AvaliacaoP2.dao.PacienteDAO;
import org.AvaliacaoP2.model.Paciente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MainController {

    @FXML private TextField txtNome;
    @FXML private TextField txtCPF;
    @FXML private TextField txtDN;
    @FXML private TextField txtFone;
    @FXML private TextField txtPesquisar;

    @FXML private TableView<Paciente> tablePacientes;

    @FXML private TableColumn<Paciente, Integer> colId;
    @FXML private TableColumn<Paciente, String> colNome;
    @FXML private TableColumn<Paciente, String> colCPF;
    @FXML private TableColumn<Paciente, String> colFone;

    private final PacienteDAO pacienteDAO = new PacienteDAO();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colFone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        atualizarTabela();
    }

    // ------------------ SALVAR ------------------
    @FXML
    private void onSalvarClick() {
        try {
            String nome = txtNome.getText().trim();
            String cpf = txtCPF.getText().trim();
            String fone = txtFone.getText().trim();
            String dataStr = txtDN.getText().trim();

            if (nome.isEmpty() || cpf.isEmpty() || fone.isEmpty() || dataStr.isEmpty()) {
                System.out.println("Preencha todos os campos!");
                return;
            }

            LocalDate dataNascimento;
            try {
                dataNascimento = LocalDate.parse(dataStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido! Use dd/MM/yyyy");
                return;
            }

            Paciente paciente = new Paciente();
            paciente.setNome(nome);
            paciente.setCpf(cpf);
            paciente.setTelefone(fone);
            paciente.setDataNascimento(dataNascimento);

            pacienteDAO.salvarPaciente(paciente);
            atualizarTabela();
            limparCampos();
            System.out.println("Paciente salvo com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------ ATUALIZAR CADASTRO ------------------
    @FXML
    private void onAtualizarCadastroClick() {
        Paciente pacienteSelecionado = tablePacientes.getSelectionModel().getSelectedItem();
        if (pacienteSelecionado == null) {
            System.out.println("Selecione um paciente para atualizar.");
            return;
        }

        String nome = txtNome.getText().trim();
        String cpf = txtCPF.getText().trim();
        String fone = txtFone.getText().trim();
        String dataStr = txtDN.getText().trim();

        if (nome.isEmpty() || cpf.isEmpty() || fone.isEmpty() || dataStr.isEmpty()) {
            System.out.println("Preencha todos os campos!");
            return;
        }

        LocalDate dataNascimento;
        try {
            dataNascimento = LocalDate.parse(dataStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido! Use dd/MM/yyyy");
            return;
        }

        pacienteSelecionado.setNome(nome);
        pacienteSelecionado.setCpf(cpf);
        pacienteSelecionado.setTelefone(fone);
        pacienteSelecionado.setDataNascimento(dataNascimento);

        pacienteDAO.atualizarPaciente(pacienteSelecionado);
        tablePacientes.refresh();
        System.out.println("Paciente atualizado com sucesso!");
    }

    // ------------------ PESQUISAR ------------------
    @FXML
    private void onPesquisarClick() {
        String filtro = txtPesquisar.getText().trim();
        if (filtro.isEmpty()) {
            atualizarTabela();
            return;
        }
        tablePacientes.getItems().setAll(pacienteDAO.buscarPorNome(filtro));
    }

    // ------------------ DELETAR ------------------
    @FXML
    private void onDeletarClick() {
        Paciente selecionado = tablePacientes.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            System.out.println("Selecione um paciente para deletar.");
            return;
        }

        pacienteDAO.deletarPaciente(selecionado.getId());
        atualizarTabela();
        limparCampos();
        System.out.println("Paciente deletado com sucesso!");
    }

    // ------------------ AUXILIARES ------------------
    private void atualizarTabela() {
        tablePacientes.getItems().setAll(pacienteDAO.buscarPacientes());
    }

    private void limparCampos() {
        txtNome.clear();
        txtCPF.clear();
        txtDN.clear();
        txtFone.clear();
        txtPesquisar.clear();
    }
}
