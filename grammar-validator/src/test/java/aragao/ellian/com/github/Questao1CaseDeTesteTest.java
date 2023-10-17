package aragao.ellian.com.github;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Questao1CaseDeTesteTest {
    private final Questao1CaseDeTeste questao1CaseDeTeste = new Questao1CaseDeTeste();

    @Test
    void validateCaseNull() {
        assertFalse(questao1CaseDeTeste.verificarGramatica(null));
    }

    @Test
    void validateCaseTrue() {
        assertTrue(questao1CaseDeTeste.verificarGramatica("baba"));
    }

    @Test
    void validateCaseFalse() {
        assertFalse(questao1CaseDeTeste.verificarGramatica("agua"));
        assertFalse(questao1CaseDeTeste.verificarGramatica("canto"));
    }
}