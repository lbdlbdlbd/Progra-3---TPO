
/* 
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
*/

//codigo de prueba

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

class Laberinto {
    private char[][] mapa;
    private int filas;
    private int columnas;
    private Point entrada;
    private Point salida;
    private Set<Character> portales;

    public Laberinto(char[][] mapa) {
        this.mapa = mapa;
        this.filas = mapa.length;
        this.columnas = mapa[0].length;
        this.portales = new HashSet<>();
        encontrarEntradaYSalida();
    }

    private void encontrarEntradaYSalida() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                char c = mapa[i][j];
                if (c == 'E') {
                    entrada = new Point(i, j);
                } else if (c == 'S') {
                    salida = new Point(i, j);
                } else if ('a' <= c && c <= 'z') {
                    portales.add(c);
                }
            }
        }
    }

    public int resolverLaberinto() {
        return backtracking(entrada.x, entrada.y, 0);
    }

    private int backtracking(int x, int y, int pasos) {
        if (x == salida.x && y == salida.y) {
            return pasos;
        }

        int minPasos = Integer.MAX_VALUE;

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (esValido(nx, ny) && mapa[nx][ny] != '#') {
                char c = mapa[nx][ny];

                if (c == '.' || c == 'S') {
                    char original = mapa[x][y];
                    mapa[x][y] = '#'; // Marcamos la casilla actual como visitada

                    int resultado = backtracking(nx, ny, pasos + 1);
                    minPasos = Math.min(minPasos, resultado);

                    mapa[x][y] = original; // Restauramos la casilla actual
                } else if (portales.contains(c)) {
                    for (Point portal : encontrarPortales(c)) {
                        char original = mapa[x][y];
                        mapa[x][y] = '#'; // Marcamos la casilla actual como visitada

                        int resultado = backtracking(portal.x, portal.y, pasos + 1);
                        minPasos = Math.min(minPasos, resultado);

                        mapa[x][y] = original; // Restauramos la casilla actual
                    }
                }
            }
        }

        return (minPasos == Integer.MAX_VALUE) ? -1 : minPasos;
    }

    private boolean esValido(int x, int y) {
        return x >= 0 && x < filas && y >= 0 && y < columnas;
    }

    private Point[] encontrarPortales(char portalTipo) {
        return portales.stream()
                .filter(p -> p == portalTipo)
                .map(p -> encontrarPosicionPortal(p))
                .toArray(Point[]::new);
    }

    private Point encontrarPosicionPortal(char portalTipo) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (mapa[i][j] == portalTipo) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};
}

public class Main {
    public static void main(String[] args) {
        char[][] laberinto1 = {
                {'E', '.', '.'},
                {'.', '.', '.'},
                {'.', '.', 'S'}
        };

        char[][] laberinto2 = {
                {'E', '#', '.'},
                {'.', '#', '.'},
                {'.', '#', 'S'}
        };

        char[][] laberinto3 = {
                {'E', '.', 'S'},
                {'.', '.', '.'},
                {'.', '.', 'S'}
        };

        char[][] laberinto4 = {
                {'S', '.', 'b', '#', 'b'},
                {'#', '#', '#', '#', 'a'},
                {'.', '.', 'E', '#', '#'},
                {'c', '#', '#', '.', 'c'},
                {'#', 'a', '.', '.', '.'}
        };

        Laberinto l1 = new Laberinto(laberinto1);
        System.out.println("Pasos laberinto1: " + l1.resolverLaberinto()); // Salida esperada: 4

        Laberinto l2 = new Laberinto(laberinto2);
        System.out.println("Pasos laberinto2: " + l2.resolverLaberinto()); // Salida esperada: -1

        Laberinto l3 = new Laberinto(laberinto3);
        System.out.println("Pasos laberinto3: " + l3.resolverLaberinto()); // Salida esperada: 2

        Laberinto l4 = new Laberinto(laberinto4);
        System.out.println("Pasos laberinto4: " + l4.resolverLaberinto()); // Salida esperada: 13
    }
}
