import java.util.*;

public class Main {

    // Função para encontrar os não-terminais que podem derivar em ε
    public static Set<String> encontrarNaoTerminaisDerivamEpsilon(Map<String, List<List<String>>> producoes) {
        Set<String> naoTerminaisDerivamEpsilon = new HashSet<>();

        for (Map.Entry<String, List<List<String>>> entry : producoes.entrySet()) {
            String simbolo = entry.getKey();
            List<List<String>> producoesSimbolo = entry.getValue();

            for (List<String> producao : producoesSimbolo) {
                if (producao.size() == 1 && producao.get(0).equals("<ε>")) {
                    naoTerminaisDerivamEpsilon.add(simbolo);
                    break;
                }
            }
        }

        boolean mudou = true;
        while (mudou) {
            mudou = false;

            for (Map.Entry<String, List<List<String>>> entry : producoes.entrySet()) {
                String simbolo = entry.getKey();
                List<List<String>> producoesSimbolo = entry.getValue();

                for (List<String> producao : producoesSimbolo) {
                    boolean todosDerivamEpsilon = true;
                    for (String componente : producao) {
                        if (!componente.equals("<ε>") && !naoTerminaisDerivamEpsilon.contains(componente)) {
                            todosDerivamEpsilon = false;
                            break;
                        }
                    }

                    if (todosDerivamEpsilon && !naoTerminaisDerivamEpsilon.contains(simbolo)) {
                        naoTerminaisDerivamEpsilon.add(simbolo);
                        mudou = true;
                        break;
                    }
                }

                if (mudou) {
                    break;
                }
            }
        }

        return naoTerminaisDerivamEpsilon;
    }

    // Função para remover produções vazias
    public static Map<String, List<List<String>>> removerProducoesVazias(Map<String, List<List<String>>> producoes,
                                                                         Set<String> naoTerminaisDerivamEpsilon) {
        Map<String, List<List<String>>> novasProducoes = new HashMap<>();

        for (Map.Entry<String, List<List<String>>> entry : producoes.entrySet()) {
            String simbolo = entry.getKey();
            List<List<String>> producoesSimbolo = entry.getValue();

            List<List<String>> novasProducoesSimbolo = new ArrayList<>();
            for (List<String> producao : producoesSimbolo) {
                if (producao.size() == 1 && producao.get(0).equals("<ε>")) {
                    continue;
                }

                boolean derivouEpsilon = false;
                for (String componente : producao) {
                    if (naoTerminaisDerivamEpsilon.contains(componente)) {
                        derivouEpsilon = true;
                        break;
                    }
                }

                if (!derivouEpsilon) {
                    novasProducoesSimbolo.add(new ArrayList<>(producao));
                } else {
                    // Gera todas as combinações de derivação epsilon
                    List<List<String>> combinacoes = gerarCombinacoesDerivamEpsilon(producao, naoTerminaisDerivamEpsilon);
                    novasProducoesSimbolo.addAll(combinacoes);
                }
            }

            novasProducoes.put(simbolo, novasProducoesSimbolo);
        }

        return novasProducoes;
    }

    // Função para gerar todas as combinações de derivação epsilon para uma produção
    public static List<List<String>> gerarCombinacoesDerivamEpsilon(List<String> producao, Set<String> naoTerminaisDerivamEpsilon) {
        List<List<String>> combinacoes = new ArrayList<>();
        List<String> combinacaoAtual = new ArrayList<>();
        gerarCombinacoesDerivamEpsilonAux(producao, 0, combinacaoAtual, combinacoes, naoTerminaisDerivamEpsilon);
        return combinacoes;
    }

    // Função auxiliar para gerar combinações
    private static void gerarCombinacoesDerivamEpsilonAux(List<String> producao, int indice, List<String> combinacaoAtual, List<List<String>> combinacoes, Set<String> naoTerminaisDerivamEpsilon) {
        if (indice == producao.size()) {
            combinacoes.add(new ArrayList<>(combinacaoAtual));
            return;
        }

        String componente = producao.get(indice);

        if (naoTerminaisDerivamEpsilon.contains(componente)) {
            gerarCombinacoesDerivamEpsilonAux(producao, indice + 1, combinacaoAtual, combinacoes, naoTerminaisDerivamEpsilon);

            List<String> novaCombinacao = new ArrayList<>(combinacaoAtual);
            novaCombinacao.add(componente);
            gerarCombinacoesDerivamEpsilonAux(producao, indice + 1, novaCombinacao, combinacoes, naoTerminaisDerivamEpsilon);
        } else {
            combinacaoAtual.add(componente);
            gerarCombinacoesDerivamEpsilonAux(producao, indice + 1, combinacaoAtual, combinacoes, naoTerminaisDerivamEpsilon);
            combinacaoAtual.remove(combinacaoAtual.size() - 1);
        }
    }

    // Função para imprimir as novas produções
    public static void imprimirProducoes(Map<String, List<List<String>>> producoes) {
        for (Map.Entry<String, List<List<String>>> entry : producoes.entrySet()) {
            String simbolo = entry.getKey();
            List<List<String>> producoesSimbolo = entry.getValue();

            for (List<String> producao : producoesSimbolo) {
                System.out.print("<" + simbolo + "> -> ");
                for (String componente : producao) {
                    System.out.print(componente + " ");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        // Defina a gramática aqui
        Map<String, List<List<String>>> producoes = new HashMap<>();
        producoes.put("S", Arrays.asList(
                Arrays.asList("a", "X", "b"),
                Arrays.asList("X", "Y"),
                Arrays.asList("W", "a")
        ));
        producoes.put("X", Arrays.asList(
                Arrays.asList("<ε>"),
                Arrays.asList("Y", "X", "a")
        ));
        producoes.put("Y", Arrays.asList(
                Arrays.asList("W", "a"),
                Arrays.asList("Z", "a", "c")
        ));
        producoes.put("Z", Arrays.asList(
                Arrays.asList("S", "Y", "a"),
                Arrays.asList("W", "b", "Z"),
                Arrays.asList("X")
        ));
        producoes.put("W", Arrays.asList(
                Arrays.asList("a", "X", "c"),
                Arrays.asList("Z")
        ));

        // Passo 1: Encontrar não-terminais que derivam ε
        Set<String> naoTerminaisDerivamEpsilon = encontrarNaoTerminaisDerivamEpsilon(producoes);

        // Passo 2: Remover produções vazias
        Map<String, List<List<String>>> novasProducoes = removerProducoesVazias(producoes, naoTerminaisDerivamEpsilon);

        // Imprimir as novas produções
        imprimirProducoes(novasProducoes);
    }
}