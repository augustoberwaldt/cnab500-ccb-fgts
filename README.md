# CNAB 500 CCB FGTS - Biblioteca Java 21

Biblioteca para geração de arquivos CNAB 500 no padrão CCB FGTS.

## Requisitos
- Java 21+
- Maven 3.9+

## Instalação
```bash
mvn clean install
```

## Uso Rápido

```java
var generator = new Cnab500Generator();

var header = new HeaderRegistro(
    "00000041197066000109",  // 6 zeros + CNPJ do fundo
    "NOME DO FUNDO",
    "104",                   // Código do banco
    "CAIXA ECONOMICA",
    LocalDate.now(),         // Data gravação
    1,                       // Sequencial arquivo
    LocalDate.now(),         // Data cessão
    "S",                     // Coobrigação
    1,                       // Natureza operação
    "CCB",                   // Segmento
    1                        // Tipo cálculo
);

var detalhe = new DetalheRegistro(
    TipoInscricao.CNPJ,
    "41197066000109",
    "CCB000000001",
    "TITULO-001",
    new BigDecimal("15000.50"),   // Valor liquidação
    LocalDate.of(2024, 6, 15),   // Data liquidação
    TipoOcorrencia.AQUISICAO,
    LocalDate.of(2024, 12, 15),  // Vencimento
    new BigDecimal("20000.00"),  // Valor nominal
    "70", "A",
    LocalDate.of(2024, 3, 1),   // Data emissão
    new BigDecimal("18500.75"), // Valor aquisição
    0, null,
    TipoInscricao.CPF,
    "12345678901",
    "JOAO DA SILVA",
    "RUA EXEMPLO 123",
    "01001000",
    "P"
);

// Gerar e salvar
generator.writeToFile(
    Path.of("saida.REM"), header, List.of(detalhe));

// Gerar nome do arquivo no padrão
String nome = CnabFormatter.buildFileName(
    "41197066000109", LocalDate.now(), "R", 1);
// => 41197066000109_20240315_R_001.REM
```

## Estrutura

| Classe | Descrição |
|---|---|
| `HeaderRegistro` | Record com campos do registro tipo 0 (header) |
| `DetalheRegistro` | Record com campos do registro tipo 1 (detalhe) |
| `Cnab500Generator` | Gerador principal - monta linhas de 500 caracteres |
| `CnabFormatter` | Utilitários de formatação (alfa, numérico, datas, moeda) |
| `TipoOcorrencia` | Enum com todos os códigos de ocorrência |
| `TipoInscricao` | Enum CPF/CNPJ |

## Códigos de Ocorrência

| Código | Descrição |
|---|---|
| 01 | Aquisição |
| 03 | Liquidação Normal |
| 10 | Baixa por Renegociação |
| 12 | Entrada por Renegociação |
| 13 | Liquidação Antecipada |
| 14 | Liquidação Parcial |
| 70 | Resolução por Fraude |
| 71 | Resolução por Cancelamento |
| 72 | Baixa por Óbito |
| 76 | Alienação para Terceiros |
| 83 | Recompra |

## Regras do Arquivo
- Codificação: UTF-8 ou ANSI
- 500 caracteres por linha
- Campos alfanuméricos (X): alinhados à esquerda, preenchidos com espaços
- Campos numéricos (9): alinhados à direita, preenchidos com zeros
- Valores monetários: 2 casas decimais sem pontuação
