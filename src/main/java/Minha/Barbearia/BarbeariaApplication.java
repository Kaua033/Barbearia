package Minha.Barbearia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@SpringBootApplication
public class BarbeariaApplication {

    /*
     * Ponto de entrada da aplicação Spring Boot.
     *
     * 1º - loadDotenv() carrega variáveis do arquivo .env
     *      (como DB_URL, DB_USERNAME, DB_PASSWORD) para System.properties.
     * 2º - SpringApplication.run() sobe o contexto do Spring.
     */
    public static void main(String[] args) {
        loadDotenv();
        SpringApplication.run(BarbeariaApplication.class, args);
    }

    /*
     * Lê o arquivo .env da raiz do projeto.
     *
     * Cada linha "CHAVE=valor" vira System.setProperty(CHAVE, valor).
     * Só seta se a variável de ambiente do sistema NÃO existir,
     * dando prioridade para variáveis reais do sistema.
     * Linhas começando com "#" são ignoradas (comentários).
     */
    private static void loadDotenv() {
        Path path = Path.of(".env");
        if (!Files.exists(path)) {
            System.err.println("Arquivo .env não encontrado, usando variáveis de ambiente do sistema");
            return;
        }
        try {
            Properties props = new Properties();
            try (var lines = Files.lines(path)) {
                lines.filter(line -> line.contains("=") && !line.strip().startsWith("#"))
                        .forEach(line -> {
                            String[] parts = line.split("=", 2);
                            String key = parts[0].strip();
                            String value = parts[1].strip();
                            if (System.getenv(key) == null) {
                                System.setProperty(key, value);
                            }
                        });
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar .env: " + e.getMessage());
        }
    }

}
