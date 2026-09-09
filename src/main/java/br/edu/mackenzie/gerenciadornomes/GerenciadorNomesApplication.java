package br.edu.mackenzie.gerenciadornomes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GerenciadorNomesApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                GerenciadorNomesApplication.class,
                args
        );
    }
}