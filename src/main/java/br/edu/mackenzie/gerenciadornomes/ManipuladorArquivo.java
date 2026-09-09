package br.edu.mackenzie.gerenciadornomes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManipuladorArquivo {
  public List<String> lerLinhas(String caminho) throws IOException {
    List<String> linhas = new ArrayList<>();

    try (BufferedReader leitor = new BufferedReader(new FileReader(caminho))) {
      String linha;

      while ((linha = leitor.readLine()) != null) {
        linhas.add(linha);
      }
    }

    return linhas;
  }

  public void escreverLinhas(String caminho, List<String> linhas) throws IOException {
    try (BufferedWriter escritor = new BufferedWriter(new FileWriter(caminho))) {
      for (String linha : linhas) {
        escritor.write(linha);
        escritor.newLine();
      }
    }
  }
}