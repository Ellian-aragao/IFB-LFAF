package aragao.ellian.com.github;

import org.junit.jupiter.api.Test;

import static aragao.ellian.com.github.Main.verificarGramatica;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void validateCasebaba() {
        String palavra = "baba";
        assertTrue(verificarGramatica(palavra));
    }
}