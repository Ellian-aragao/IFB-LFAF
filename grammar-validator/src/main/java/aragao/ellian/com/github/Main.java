package aragao.ellian.com.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

public class Main {
    final static Logger log = LoggerFactory.getLogger(Main.class);
    private static final HashMap<String, Map<String, String>> mapper = new HashMap<>();
    private static final Optional<HashMap<String, Map<String, String>>> gramaticaMapperOpt = Optional.of(mapper);

    static {
        mapper.put("S", Map.of("a", "XY", "b", "XY"));
        mapper.put("X", Map.of(
                "a", "aA",
                "b", "bB",
                "F", "F"
        ));
        mapper.put("Y", Map.of("a", "Ya"));
        mapper.put("Aa", Map.of("a", "aA"));
        mapper.put("Ab", Map.of("b", "bA"));
        mapper.put("AY", Map.of("a", "Ya"));
        mapper.put("Ba", Map.of("a", "aB"));
        mapper.put("Bb", Map.of("b", "bB"));
        mapper.put("BY", Map.of("b", "Yb"));
        mapper.put("Fa", Map.of("a", "aF"));
        mapper.put("Fb", Map.of("b", "bF"));
    }

    public static boolean verificarGramatica(String palavra) {
        var derivacoes = new LinkedList<String>();
        derivacoes.add("S");
        log.debug("adicionando letra S");

        for (int i = 0; i < palavra.length(); i++) {
            var simbolo = String.valueOf(palavra.charAt(i));
            log.debug("iteracao {} -> simbolo {}", i, simbolo);
            final var novasDerivacoes = new LinkedList<String>();

            for (final var derivacao : derivacoes) {
                log.debug("derivacao a ser utilizada: {}", derivacao);
                final var novasDerivacoesTemp = derivar(derivacao, simbolo);
                novasDerivacoesTemp.ifPresent(novasDerivacoes::add);
                novasDerivacoesTemp.ifPresentOrElse(
                        derivacaoString -> log.debug("derivacao encontrada: {}", derivacaoString),
                        () -> log.debug("nenhuma derivacao encontrada")
                );
            }

            if (novasDerivacoes.isEmpty()) {
                log.info("validacao da palavra '{}' resutou em false", palavra);
                return false;
            }

            derivacoes = novasDerivacoes;
        }

        return derivacoes.contains("F");
    }

    public static Optional<String> derivar(String derivacao, String simbolo) {
        if ("FY".equals(derivacao)) return Optional.of("");

        return gramaticaMapperOpt.map(mapString -> mapString.get(derivacao))
                .map(mapString -> mapString.get(simbolo));
    }
}
