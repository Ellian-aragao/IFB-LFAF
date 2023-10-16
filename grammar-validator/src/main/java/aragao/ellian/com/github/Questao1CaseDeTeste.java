package aragao.ellian.com.github;

import aragao.ellian.com.github.commons.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.*;

public class Questao1CaseDeTeste {
    final static Logger log = LoggerFactory.getLogger(Questao1CaseDeTeste.class);
    private final Set<String> alfabeto = Set.of("a", "b");
    private final Map<String, List<String>> mapper;

    public Questao1CaseDeTeste() {
        mapper = new HashMap<>();
        mapper.put("S", List.of("XY"));
        mapper.put("X", List.of("XaA", "XbB", "F"));
        mapper.put("Aa", List.of("aA"));
        mapper.put("Ab", List.of("bA"));
        mapper.put("AY", List.of("Ya"));
        mapper.put("Ba", List.of("aB"));
        mapper.put("Bb", List.of("bB"));
        mapper.put("BY", List.of("Yb"));
        mapper.put("Fa", List.of("aF"));
        mapper.put("Fb", List.of("bF"));
        mapper.put("FY", List.of(""));
    }

    public boolean verificarGramatica( String palavra) {
        if (alfabeto.stream().allMatch(palavra::contains)) return false;

        final var palavraGramaticaInicial = "S";
        return verificarGramatica(Tuple2.of(palavraGramaticaInicial, palavra));
    }

    private boolean verificarGramatica(@NotNull Tuple2<String, String> parsingPalavraEPalavra) {
        final var palavra = parsingPalavraEPalavra.obj1();
        final var parsingPalavra = parsingPalavraEPalavra.obj2();

        log.debug("Current Parsing: {}", parsingPalavra);
        if (parsingPalavra.length() >= palavra.length() + 10) return false;

        return mapper.entrySet()
                .stream()
                .filter(entry -> parsingPalavra.contains(entry.getKey()))
                .findFirst()
                .map(values ->
                        values.getValue().stream().map(value -> {
                                    final var palavraGramaticaAlterada = parsingPalavra.replace(values.getKey(), value);
                                    log.debug("Replacing {} with {} => {}", palavra, value, palavraGramaticaAlterada);
                                    final var parsingPalavraEPalavra1 = Tuple2.of(palavraGramaticaAlterada, palavra);
                                    return palavra.equals(palavraGramaticaAlterada) || verificarGramatica(parsingPalavraEPalavra1);
                                })
                                .toList()
                )
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(Boolean.TRUE::equals);
    }
}
