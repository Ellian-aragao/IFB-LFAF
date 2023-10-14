package aragao.ellian.com.github;

import org.junit.jupiter.api.Test;

import static aragao.ellian.com.github.Main.verificarGramatica;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    void validateCasebaba() {
        String palavra = "baba";
        assertTrue(verificarGramatica(palavra));
    }

    @Test
    void validateCaseagua() {
        String palavra = "agua";
        assertFalse(verificarGramatica(palavra));
    }

    @Test
    void validateCasecanto() {
        String palavra = "canto";
        assertFalse(verificarGramatica(palavra));
    }
}