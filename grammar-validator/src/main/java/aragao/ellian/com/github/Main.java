package aragao.ellian.com.github;

import java.util.*;
import java.util.stream.Stream;

public class Main {

    public static boolean verificarGramatica(String palavra) {
        var derivacoes = new LinkedList<String>();
        derivacoes.add("S");

        for (int i = 0; i < palavra.length(); i++) {
            var simbolo = String.valueOf(palavra.charAt(i));
            final var novasDerivacoes = new LinkedList<String>();

            for (String derivacao : derivacoes) {
                final var novasDerivacoesTemp = derivar(derivacao, simbolo);
                novasDerivacoesTemp.ifPresent(novasDerivacoes::add);
            }

            if (novasDerivacoes.isEmpty()) {
                return false;
            }

            derivacoes = novasDerivacoes;
        }

        return derivacoes.contains("F");
    }

    public static Optional<String> derivar(String derivacao, String simbolo) {
        final var mapper = new HashMap<String, Map<String, String>>();
        mapper.put("S", Map.of("a", "XY"));
        mapper.put("X", Map.of(
                "a", "XaA",
                "b", "XbB",
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

        if ("FY".equals(derivacao)) return Optional.of("");

        return Optional.of(mapper)
                .map(mapString -> mapString.get(derivacao))
                .map(mapString -> mapString.get(simbolo));
    }
}
