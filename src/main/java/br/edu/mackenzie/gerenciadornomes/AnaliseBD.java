package br.edu.mackenzie.gerenciadornomes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AnaliseBD {

    private final Connection connection;

    public AnaliseBD(Connection connection) {
        this.connection = connection;
    }

    public boolean salvar(
            String arquivo,
            int registrosValidos,
            int quantidadeFalhas,
            double tempoMedioPayments) {

        String sql = """
            INSERT INTO analises
            (arquivo, registros_validos, quantidade_falhas, tempo_medio_payments)
            VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, arquivo);
            statement.setInt(2, registrosValidos);
            statement.setInt(3, quantidadeFalhas);
            statement.setDouble(4, tempoMedioPayments);

            int quantidade = statement.executeUpdate();

            return quantidade > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void exibirHistorico() {

        String sql = """
            SELECT id, arquivo, registros_validos,
                   quantidade_falhas, tempo_medio_payments
            FROM analises
            ORDER BY id
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\n===== HISTÓRICO DE ANÁLISES =====");

            while (resultSet.next()) {

                System.out.println("ID: "
                        + resultSet.getLong("id"));

                System.out.println("Arquivo: "
                        + resultSet.getString("arquivo"));

                System.out.println("Registros válidos: "
                        + resultSet.getInt("registros_validos"));

                System.out.println("Quantidade de falhas: "
                        + resultSet.getInt("quantidade_falhas"));

                System.out.println("Tempo médio /api/v1/payments: "
                        + resultSet.getDouble("tempo_medio_payments"));

                System.out.println("--------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
