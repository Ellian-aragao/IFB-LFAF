package aragao.ellian.com.github;

import java.util.LinkedList;
import java.util.List;

public class Main {

    public static boolean verificarGramatica(String palavra) {
        var derivacoes = new LinkedList<String>();
        derivacoes.add("S");

        for (int i = 0; i < palavra.length(); i++) {
            var simbolo = palavra.charAt(i);
            final var novasDerivacoes = new LinkedList<String>();

            for (String derivacao : derivacoes) {
                final var novasDerivacoesTemp = derivar(derivacao, simbolo);
                novasDerivacoes.addAll(novasDerivacoesTemp);
            }

            if (novasDerivacoes.isEmpty()) {
                return false;
            }

            derivacoes = novasDerivacoes;
        }

        return derivacoes.contains("F");
    }

    public static List<String> derivar(String derivacao, char simbolo) {
        final var novasDerivacoes = new LinkedList<String>();
        switch (derivacao) {
            case "S":
                if (simbolo == 'a') {
                    novasDerivacoes.add("XY");
                }
                break;
            case "X":
                switch (simbolo) {
                    case 'a':
                        novasDerivacoes.add("XaA");
                        break;
                    case 'b':
                        novasDerivacoes.add("XbB");
                        break;
                    case 'F':
                        novasDerivacoes.add("F");
                        break;
                }
                break;
            case "Y":
                if (simbolo == 'a') {
                    novasDerivacoes.add("Ya");
                }
                break;
            case "Aa":
                if (simbolo == 'a') {
                    novasDerivacoes.add("aA");
                }
                break;
            case "Ab":
                if (simbolo == 'b') {
                    novasDerivacoes.add("bA");
                }
                break;
            case "AY":
                if (simbolo == 'a') {
                    novasDerivacoes.add("Ya");
                }
                break;
            case "Ba":
                if (simbolo == 'a') {
                    novasDerivacoes.add("aB");
                }
                break;
            case "Bb":
                if (simbolo == 'b') {
                    novasDerivacoes.add("bB");
                }
                break;
            case "BY":
                if (simbolo == 'b') {
                    novasDerivacoes.add("Yb");
                }
                break;
            case "Fa":
                if (simbolo == 'a') {
                    novasDerivacoes.add("aF");
                }
                break;
            case "Fb":
                if (simbolo == 'b') {
                    novasDerivacoes.add("bF");
                }
                break;
            case "FY":
                novasDerivacoes.add("");
                break;
        }

        return novasDerivacoes;
    }
}
