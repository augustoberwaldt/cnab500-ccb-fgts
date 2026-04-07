package br.com.cnab500.parser;

import br.com.cnab500.enums.TipoInscricao;
import br.com.cnab500.enums.TipoOcorrencia;
import br.com.cnab500.generator.Cnab500Generator;
import br.com.cnab500.model.DetalheRegistro;
import br.com.cnab500.model.DetalheRegistroBuilder;
import br.com.cnab500.model.HeaderRegistro;
import br.com.cnab500.model.HeaderRegistroBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Cnab500ParserTest {

    @TempDir
    Path tempDir;

    @Test
    void parseFile_deveLerArquivoGeradoEMapearCampos() throws IOException {

        HeaderRegistro header = HeaderRegistroBuilder.builder()
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

        DetalheRegistro detalhe = DetalheRegistroBuilder.builder()
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

        Cnab500Generator generator = new Cnab500Generator();
        Path arquivo = tempDir.resolve("arquivo.rem");
        Files.write(arquivo, generator.generate(header, List.of(detalhe)), StandardCharsets.UTF_8);

        Cnab500Parser parser = new Cnab500Parser();
        var cnab = parser.parseFile(arquivo);

        assertNotNull(cnab);
        assertNotNull(cnab.header());
        assertEquals(3, cnab.totalRegistros());
        assertEquals(1, cnab.detalhes().size());

        assertEquals("00000041197066000109", cnab.header().codigoOriginador());
        assertEquals("FUNDO EXEMPLO LTDA", cnab.header().nomeCedente());
        assertEquals(LocalDate.of(2025, 3, 26), cnab.header().dataGravacao());

        DetalheRegistro detalheParseado = cnab.detalhes().get(0);
        assertEquals(TipoInscricao.CNPJ, detalheParseado.tipoInscricaoCedente());
        assertEquals("41197066000109", detalheParseado.inscricaoCedente());
        assertEquals("CCB000000001", detalheParseado.numeroContrato());
        assertEquals("TITULO-001", detalheParseado.seuNumero());
        assertEquals(new BigDecimal("15000.50"), detalheParseado.valorLiquidacao());
        assertEquals(LocalDate.of(2025, 12, 15), detalheParseado.dataVencimento());
        assertEquals(TipoOcorrencia.AQUISICAO, detalheParseado.ocorrencia());
        assertEquals(new BigDecimal("18500.75"), detalheParseado.valorAquisicao());
        assertEquals(TipoInscricao.CPF, detalheParseado.tipoInscricaoSacado());
        assertEquals("12345678901", detalheParseado.inscricaoSacado());
        assertEquals("JOAO DA SILVA", detalheParseado.nomeSacado());
        assertEquals("RUA EXEMPLO 123 CENTRO", detalheParseado.enderecoSacado());
        assertEquals("01001000", detalheParseado.cepSacado());
        assertEquals("P", detalheParseado.tipoAtivo());
        assertTrue(detalheParseado.correcaoIndice().compareTo(BigDecimal.ZERO) == 0);
    }

    @Test
    void parseFile_deveLancarExcecaoQuandoLinhaNaoTem500Caracteres() throws IOException {

        Path arquivoInvalido = tempDir.resolve("invalido.rem");
        Files.writeString(arquivoInvalido, "0".repeat(499), StandardCharsets.UTF_8);

        Cnab500Parser parser = new Cnab500Parser();

        CnabParseException ex = assertThrows(CnabParseException.class, () -> parser.parseFile(arquivoInvalido));
        assertTrue(ex.getMessage().contains("Linha 1 tem 499 caracteres"));
    }
}

