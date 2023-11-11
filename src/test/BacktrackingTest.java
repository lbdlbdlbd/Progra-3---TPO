package test;

import labertinto.EntradaFaltanteException;
import labertinto.Mapa;
import org.junit.Test;


import static junit.framework.TestCase.*;
import static org.junit.Assert.assertThrows;

public class BacktrackingTest {

    @Test
    public void laberintoTest() {
        Mapa mapa = new Mapa(new char[][]{{'.', '.', '.'}, {'.', '.', '.'}, {'.', '.', 'S'}});
        assertThrows(EntradaFaltanteException.class, mapa::escapar);
        Mapa mapa1 = new Mapa(new char[][]{{'E', '.', '.'}, {'.', '.', '.'}, {'.', '.', 'S'}});
        assertEquals(4, mapa1.escapar());
        Mapa mapa2 = new Mapa(new char[][]{{'E', '#', '.'}, {'.', '#', '.'}, {'.', '#', 'S'}});
        assertEquals(-1, mapa2.escapar());
        Mapa mapa3 = new Mapa(new char[][]{{'E', '.', 'S'}, {'.', '.', '.'}, {'.', '.', 'S'}});
        assertEquals(2, mapa3.escapar());
        Mapa mapa4 = new Mapa(new char[][]{{'E', '#', '.', '.'}, {'.', '.', '.', '.'}, {'#', '.', '#', '.'}, {'.', '#', '#', 'S'}});
        assertEquals(6, mapa4.escapar());
        Mapa mapa5 = new Mapa(new char[][]{{'S', '.', 'b', '#', 'b'}, {'#', '#', '#', '#', 'a'}, {'.', '.', 'E', '#', '#'}, {'c', '#', '#', '.', 'c'}, {'#', 'a', '.', '.', '.'}});
        assertEquals(-1, mapa5.escapar());
        Mapa mapa6 = new Mapa(new char[][]{
                {'S', '.', 'b','#','b'},
                {'#', '.', '#','#','a'},
                {'#', '.', 'E','#','#'},
                {'c', '#', '#','.','c'},
                {'#', 'a', '.','.','.'} });
        assertEquals(4, mapa6.escapar());
    }
}