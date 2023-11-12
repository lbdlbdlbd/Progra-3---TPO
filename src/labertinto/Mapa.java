package labertinto;

import java.util.*;

public class Mapa {
    private static final char ENTRADA = 'E';
    private static final char PARED = '#';
    private static final char SALIDA = 'S';
    private static final char[] PORTAL = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','u','v','w','x','y','z'};
    private static final String abcd = "abcdefghijklmnopqrsuvwxyz";
    private static final int[][] POSIBLES_MOVIMIENTOS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private final char[][] mapa;

    public Mapa(char[][] mapa) {
        this.mapa = mapa;
    }

    private int obtenerCantidadDePasos(Celda celda) {                   // a=  b=  k=
        Queue<Nodo> cola = new LinkedList<>();
        Set<Nodo> visitados = new HashSet<>();
        List<Nodo> vecinos;
        Nodo actual = new Nodo(celda, 0);
        cola.add(actual);
        visitados.add(actual);
        while (!cola.isEmpty()) {
            Nodo primero = cola.poll();
            if (this.esSalida(primero.getCelda())) {
                return primero.getPasos();
            }
            vecinos = this.posiblesMovimientosDesdeCoordenada(primero);                      //O(n^2)
            while (!vecinos.isEmpty()) {
                Nodo vecino = vecinos.remove(0);
                if (!visitados.contains(vecino)) {
                    cola.add(vecino);
                    visitados.add(vecino);
                }
            }
        }
        return -1;
    }

    private List<Nodo> posiblesMovimientosDesdeCoordenada(Nodo nodo) {                              //O(n^2)
        List<Nodo> posiblesMovimientos = new ArrayList<>();
        int x = nodo.getPosicionX();
        int y = nodo.getPosicionY();
        int pasosHastaAhora = nodo.getPasos();
        for (int[] movimiento : POSIBLES_MOVIMIENTOS) {                                             //O(4)
            Celda siguienteCelda = new Celda(x + movimiento[0], y + movimiento[1]);
            if (!this.estaFueraDeMapa(siguienteCelda) && !this.esPared(siguienteCelda)) {
                if (this.esPortal(siguienteCelda)){                                                 //O(log2n)
                    siguienteCelda = buscarPosicionPortal(siguienteCelda, this.mapa[siguienteCelda.getX()][siguienteCelda.getY()]); //O(n^2)
                    posiblesMovimientos.add(new Nodo(siguienteCelda, pasosHastaAhora + 2));
                }
                else {
                    posiblesMovimientos.add(new Nodo(siguienteCelda, pasosHastaAhora + 1));
                }
            }
        }
        return posiblesMovimientos;
    }

    private boolean esPared(Celda celda) {                                    //O(c)
        return this.mapa[celda.getX()][celda.getY()] == PARED;
    }

    private boolean estaFueraDeMapa(Celda celda) {                            //O(c)
        int x = celda.getX();
        int y = celda.getY();
        return x < 0 || y < 0 || x >= this.mapa.length || y >= this.mapa[x].length;
    }

    private boolean esSalida(Celda celda) {                                       //O(c)
        return this.mapa[celda.getX()][celda.getY()] == SALIDA;
    }

    public int escapar() {
        Celda entrada = buscarPosicionEntrada();
        return obtenerCantidadDePasos(entrada);
    }

    private Celda buscarPosicionEntrada() {                                       //O(n^2)
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (mapa[i][j] == ENTRADA) {
                    return new Celda(i, j);
                }
            }
        }
        throw new EntradaFaltanteException("El mapa no tiene entrada");
    }

    private boolean esPortal(Celda celda) {                                                         //O(log2n)
        int res = Arrays.binarySearch(PORTAL, this.mapa[celda.getX()][celda.getY()]);
        boolean test = res >= 0 ? true : false;
        return test;
    }
    private Celda buscarPosicionPortal(Celda celda, char letra) {                                   //O(n^2)
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (mapa[i][j] == letra && (i != celda.getX() || j != celda.getY())){
                    return new Celda(i, j);
                }
            }
        }
        throw new EntradaFaltanteException("No se encontro el portal");
    }
}
