package br.com.cnab500;

import br.com.cnab500.enums.TipoInscricao;
import br.com.cnab500.enums.TipoOcorrencia;
import br.com.cnab500.generator.Cnab500Generator;
import br.com.cnab500.model.DetalheRegistroBuilder;
import br.com.cnab500.model.HeaderRegistroBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

/**
 * Exemplo de uso da biblioteca CNAB 500 CCB FGTS.
 * Execute: javac + java ou via Maven test.
 */
public class Cnab500GeneratorTest {

    public static void main(String[] args) throws Exception {

        var generator = new Cnab500Generator();

        var header = HeaderRegistroBuilder.builder()
            .codigoOriginador("00000041197066000109")
            .nomeCedente("FUNDO EXEMPLO LTDA")
            .numeroBanco("104")
            .nomeBanco("PICPAY")
            .dataGravacao(LocalDate.of(2025, 3, 26))
            .sequencialArquivo(1)
            .dataCessao(LocalDate.of(2025, 3, 26))
            .coobrigacao("S")
            .naturezaOperacao(1)
            .segmento("CCB")
            .tipoCalculo(1)
            .build();

        var detalhe = DetalheRegistroBuilder.builder()
            .cedente(TipoInscricao.CNPJ, "41197066000109")
            .contrato("CCB000000001")
            .seuNumero("TITULO-001")
            .liquidacao(new BigDecimal("15000.50"), LocalDate.of(2024, 6, 15))
            .ocorrencia(TipoOcorrencia.AQUISICAO)
            .vencimento(LocalDate.of(2025, 12, 15))
            .valorNominal(new BigDecimal("20000.00"))
            .especieTitulo("70")
            .aceite("A")
            .emissao(LocalDate.of(2025, 3, 1))
            .valorAquisicao(new BigDecimal("18500.75"))
            .indice(0)
            .sacado(TipoInscricao.CPF, "12345678901", "JOAO DA SILVA")
            .enderecoSacado("RUA EXEMPLO 123 CENTRO", "01001000")
            .tipoAtivo("P")
            .build();

        var lines = generator.generate(header, List.of(detalhe));
        for (var line : lines) {
            System.out.println(line);
            System.out.println("Tamanho: " + line.length());
        }
        System.out.println("\nArquivo gerado com sucesso! Total de linhas: " + lines.size());

        // Exemplo de nome do arquivo
        String fileName = br.com.cnab500.util.CnabFormatter.buildFileName(
            "41197066000109", LocalDate.of(2025, 3, 26), "R", 1);
        System.out.println("Nome do arquivo: " + fileName);

        // Salvar arquivo na máquina
        try {
            String fileContent = String.join("\n", lines);
            Files.write(Paths.get(fileName), fileContent.getBytes());
            System.out.println("Arquivo salvo com sucesso em: " + Paths.get(fileName).toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
