package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Funcionario;

public class FuncionarioDAO {

    private Connection connection;

    public FuncionarioDAO(Connection connection) {
        this.connection = connection;
    }

    public void criarFuncionario(Funcionario funcionario) {
        try {
            String sql = "INSERT INTO funcionario (id_pessoa, cargo, nome, cpf) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.setString(1, funcionario.getIdPessoa());
                pstm.setString(2, funcionario.getCargo());
                pstm.setString(3, funcionario.getNome());
                pstm.setString(4, funcionario.getCpf());

                pstm.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Funcionario> recuperarFuncionarios() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();

        try {
            String sql = "SELECT id_pessoa, cargo, nome, cpf, telefone FROM funcionario";

            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                pstm.execute();
                ResultSet rst = pstm.getResultSet();

                while (rst.next()) {
                    String idPessoa = rst.getString("id_pessoa");
                    String cargo = rst.getString("cargo");
                    String nome = rst.getString("nome");
                    String cpf = rst.getString("cpf");
                    String telefone = rst.getString("telefone");

                    Funcionario funcionario = new Funcionario(idPessoa, cargo, nome, cpf, telefone);
                    funcionarios.add(funcionario);
                }
            }
            return funcionarios;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deletarFuncionario(String idFuncionario) {
        String sql = "DELETE FROM funcionario WHERE id_pessoa = 2";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, idFuncionario);

            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Funcionário deletado com sucesso!");
            } else {
                System.out.println("Nenhum funcionário foi deletado. Verifique o ID do funcionário.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
