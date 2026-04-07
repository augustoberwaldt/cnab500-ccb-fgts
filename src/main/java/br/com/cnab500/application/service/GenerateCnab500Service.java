package br.com.cnab500.application.service;

import br.com.cnab500.application.port.in.GenerateCnab500UseCase;
import br.com.cnab500.application.port.out.Cnab500SerializerPort;
import br.com.cnab500.model.DetalheRegistro;
import br.com.cnab500.model.HeaderRegistro;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenerateCnab500Service implements GenerateCnab500UseCase {

    private final Cnab500SerializerPort serializer;

    public GenerateCnab500Service(Cnab500SerializerPort serializer) {
        this.serializer = Objects.requireNonNull(serializer, "serializer é obrigatório");
    }

    @Override
    public List<String> generate(HeaderRegistro header, List<DetalheRegistro> detalhes) {

        Objects.requireNonNull(header, "header é obrigatório");
        Objects.requireNonNull(detalhes, "detalhes é obrigatório");

        var lines = new ArrayList<String>();
        int seq = 1;
        lines.add(serializer.serializeHeader(header, seq++));

        for (int i = 0; i < detalhes.size(); i++) {
            var detalhe = Objects.requireNonNull(detalhes.get(i), "detalhe na posição " + i + " é obrigatório");
            lines.add(serializer.serializeDetalhe(detalhe, seq++));
        }

        lines.add(serializer.serializeTrailer(seq));
        validateLineSize(lines);
        return lines;
    }

    private void validateLineSize(List<String> lines) {

        for (int i = 0; i < lines.size(); i++) {
            int lineLength = lines.get(i).length();
            if (lineLength != serializer.lineSize()) {
                throw new IllegalStateException(
                    "Linha %d tem %d caracteres (esperado %d)".formatted(i + 1, lineLength, serializer.lineSize()));
            }
        }
    }
}

