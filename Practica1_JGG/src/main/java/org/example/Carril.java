package org.example;

public class Carril {
    private final int id;
    private final Corredor[] posiciones = new Corredor[400000];
    private final int posCorredor;

    public Carril(int id, int numCorredores) {
        this.id = id;
        this.posCorredor = (numCorredores > 0) ? posiciones.length / numCorredores : posiciones.length;
    }

    public synchronized boolean anadirCorredor(Corredor corredor, int posicion) {
        if (posiciones[posicion] == null) {
            posiciones[posicion] = corredor;
            return true;
        }
        return false;
    }

    public synchronized boolean avanzarCorredor(int idCorredor) {
        int inicio = posCorredor * (idCorredor - 1);
        int fin = Math.min(posCorredor * idCorredor, posiciones.length);
        for (int i = inicio; i < fin; i++) {
            if (posiciones[i] != null && posiciones[i].getId() == idCorredor) {
                if (i + 1 < fin && posiciones[i + 1] == null) {
                    posiciones[i + 1] = posiciones[i];
                    posiciones[i] = null;
                    return true;
                }
                break;
            }
        }
        return false;
    }

    public synchronized Corredor puedePasarTestigo(int idCorredor) {
        int inicio = posCorredor * (idCorredor - 1);
        int fin = Math.min(posCorredor * idCorredor, posiciones.length);
        for (int i = inicio; i < fin; i++) {
            if (posiciones[i] != null && posiciones[i].getId() == idCorredor) {
                if (i + 1 < fin && posiciones[i + 1] != null) {
                    return posiciones[i + 1];
                }
                break;
            }
        }
        return null;
    }

    public synchronized int obtenerPosicion(int idCorredor) {
        int inicio = posCorredor * (idCorredor - 1);
        int fin = Math.min(posCorredor * idCorredor, posiciones.length);
        for (int i = inicio; i < fin; i++) {
            if (posiciones[i] != null && posiciones[i].getId() == idCorredor) {
                return i;
            }
        }
        return -1;
    }

    public int thresholdCorredor(int idCorredor) {
        return posCorredor * (idCorredor);
    }

    public int getId() {
        return id;
    }
}
