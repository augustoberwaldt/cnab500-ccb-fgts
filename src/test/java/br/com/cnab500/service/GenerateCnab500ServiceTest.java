package br.com.cnab500.service;

import br.com.cnab500.application.service.GenerateCnab500Service;
import br.com.cnab500.application.port.out.Cnab500SerializerPort;
import br.com.cnab500.enums.TipoInscricao;
import br.com.cnab500.enums.TipoOcorrencia;
import br.com.cnab500.model.DetalheRegistro;
import br.com.cnab500.model.DetalheRegistroBuilder;
import br.com.cnab500.model.HeaderRegistro;
import br.com.cnab500.model.HeaderRegistroBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GenerateCnab500ServiceTest {

    @Mock
    private Cnab500SerializerPort serializer;

    @InjectMocks
    private GenerateCnab500Service service;

    @Test
    void deveGerarArquivoCnab500() {

        // Arrange
        var header = buildHeader();
        var detalhe = buildDetalhe();

        when(serializer.lineSize()).thenReturn(500);
        when(serializer.serializeHeader(header, 1))
                .thenReturn("H".repeat(500));

        when(serializer.serializeDetalhe(detalhe, 2))
                .thenReturn("D".repeat(500));

        when(serializer.serializeTrailer(3))
                .thenReturn("T".repeat(500));
        // Act
        var result = service.generate(header, List.of(detalhe));

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(500, result.get(0).length());
        assertEquals(500, result.get(1).length());
        assertEquals(500, result.get(2).length());
    }

    // =========================
    // Builders auxiliares
    // =========================

    private HeaderRegistro buildHeader() {
        return HeaderRegistroBuilder.builder()
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
    }

    private DetalheRegistro buildDetalhe() {
        return DetalheRegistroBuilder.builder()
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
    }
}