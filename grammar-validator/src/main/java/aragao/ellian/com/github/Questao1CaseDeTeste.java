package aragao.ellian.com.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Questao1CaseDeTeste {
    final static Logger log = LoggerFactory.getLogger(Questao1CaseDeTeste.class);
    private static final long TOTAL_RECURSIVE_ITERATIONS = 10;
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

    public boolean verificarGramatica(String palavra) {
        if (Objects.isNull(palavra) || !alfabeto.stream().allMatch(palavra::contains)) return false;

        final var palavraGramaticaInicial = "S";
        return verificarGramatica(palavraGramaticaInicial, palavra, 0L);
    }

    private boolean verificarGramatica(String palavraGramaticaDerivada, String parsingPalavra, long contador) {
        log.debug("Current Parsing: {}", palavraGramaticaDerivada);
        if (contador >= TOTAL_RECURSIVE_ITERATIONS) return false;

        return mapper.entrySet()
                .stream()
                .filter(entry -> palavraGramaticaDerivada.contains(entry.getKey()))
                .findFirst()
                .map(values -> derivaPalavraGramatica(palavraGramaticaDerivada, parsingPalavra, values, contador))
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(Boolean.TRUE::equals);
    }

    private List<Boolean> derivaPalavraGramatica(String palavraGramaticaDerivada, String parsingPalavra, Map.Entry<String, List<String>> values, long contador) {
        return values.getValue()
                .stream()
                .map(value -> {
                    final var palavraGramaticaAlterada = palavraGramaticaDerivada.replace(values.getKey(), value);
                    log.debug("Replacing {} with {} => {}", palavraGramaticaDerivada, value, palavraGramaticaAlterada);
                    return parsingPalavra.equals(palavraGramaticaAlterada) || verificarGramatica(palavraGramaticaAlterada, parsingPalavra, contador + 1);
                })
                .toList();
    }
}
