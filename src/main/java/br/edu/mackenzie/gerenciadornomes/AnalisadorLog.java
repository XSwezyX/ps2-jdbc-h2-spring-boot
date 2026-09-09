package br.edu.mackenzie.gerenciadornomes;
import java.util.ArrayList;
import java.util.List;

public class AnalisadorLog implements IAnalisadorLog {
  @Override
  public int contarRegistrosValidos(List<String> linhas) {
    int registrosValidos = 0;

    for (String linha : linhas) {
      if (linhaEhValida(linha)) {
        registrosValidos++;
      }
    }

    return registrosValidos;
  }

  @Override
  public List<String> listarRequisicoesComFalha(List<String> linhas) {
    List<String> falhas = new ArrayList<>();

    for (String linha : linhas) {
      if (linhaEhValida(linha)) {
        String[] campos = linha.split("\\|");
        int status = Integer.parseInt(campos[4].trim());

        if (status >= 400) {
          falhas.add(campos[1].trim() + " | " + campos[2].trim() + " | "
              + campos[3].trim() + " | " + status + " | " + campos[5].trim());
        }
      }
    }

    return falhas;
  }

  @Override
  public double calcularTempoMedioPayments(List<String> linhas) {
    int quantidadePayments = 0;
    int somaTemposPayments = 0;

    for (String linha : linhas) {
      if (linhaEhValida(linha)) {
        String[] campos = linha.split("\\|");
        String url = campos[3].trim();

        if (url.equals("/api/v1/payments")) {
          String tempoTexto = campos[5].trim();
          int tempo = Integer.parseInt(tempoTexto.substring(0, tempoTexto.length() - 2));
          quantidadePayments++;
          somaTemposPayments += tempo;
        }
      }
    }

    if (quantidadePayments == 0) {
      return -1;
    }

    return (double) somaTemposPayments / quantidadePayments;
  }

  private boolean linhaEhValida(String linha) {
    String[] campos = linha.split("\\|");

    if (campos.length != 6 || !campos[5].trim().endsWith("ms")) {
      return false;
    }

    try {
      Integer.parseInt(campos[4].trim());
      String tempoTexto = campos[5].trim();
      Integer.parseInt(tempoTexto.substring(0, tempoTexto.length() - 2));
      return true;
    } catch (NumberFormatException erro) {
      return false;
    }
  }
}