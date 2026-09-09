package br.edu.mackenzie.gerenciadornomes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class RepositorioAnalisesBD {
    public static void main(String[] args) {
    String arquivoEntrada = args.length > 0 ? args[0] : "access.log";
    String arquivoSaida = args.length > 1 ? args[1] : "relatorio.txt";

    ManipuladorArquivo leitor = new ManipuladorArquivo();
    IAnalisadorLog analisador = new AnalisadorLog();

    try {
      List<String> linhas = leitor.lerLinhas(arquivoEntrada);
      int registrosValidos = analisador.contarRegistrosValidos(linhas);
      List<String> falhas = analisador.listarRequisicoesComFalha(linhas);
      double tempoMedioPayments = analisador.calcularTempoMedioPayments(linhas);
      List<String> relatorio = new ArrayList<>();

      relatorio.add("RELATORIO DE ANALISE DO SERVIDOR");
      relatorio.add("================================");
      relatorio.add("Registros validos processados: " + registrosValidos);
      relatorio.add("Requisicoes com falha: " + falhas.size());
      relatorio.add("");
      relatorio.add("FALHAS ENCONTRADAS");

      if (falhas.isEmpty()) {
        relatorio.add("Nenhuma");
      } else {
        relatorio.addAll(falhas);
      }

      relatorio.add("");
      relatorio.add("TEMPO MEDIO - /api/v1/payments");
      if (tempoMedioPayments < 0) {
        relatorio.add("Nao ha dados suficientes para calcular a media.");
      } else {
        relatorio.add(String.format(Locale.US, "%.2fms", tempoMedioPayments));
      }

      leitor.escreverLinhas(arquivoSaida, relatorio);
      System.out.println("Relatorio gerado em: " + arquivoSaida);
    } catch (java.io.FileNotFoundException erro) {
      System.out.println("Arquivo de entrada nao encontrado: " + arquivoEntrada);
    } catch (IOException erro) {
      System.out.println("Erro ao processar os arquivos: " + erro.getMessage());
    }
  }
}

