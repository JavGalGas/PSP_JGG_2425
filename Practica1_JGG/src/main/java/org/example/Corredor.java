package org.example;

public class Corredor implements Runnable{
    private final int id;
    private final Carril carril;
    private boolean tieneTestigo = false;

    public Corredor(int id, Carril carril) {
        this.id = id;
        this.carril = carril;
    }

    public int getId() {
        return id;
    }

    public void recibirTestigo() {
        System.out.println("Carril " + carril.getId() + " Corredor " + id + ": Recibo el testigo");
        tieneTestigo = true;
    }

    @Override
    public void run() {
        try {
            synchronized (carril) {
                while (!tieneTestigo) {
                    System.out.println("Carril " + carril.getId() + " Corredor " + id + ": A la espera de recibir el testigo");
                    carril.wait();
                    if (!tieneTestigo) {
                        System.out.println("Carril " + carril.getId() + " Corredor " + id + ": Me han despertado. No tengo el testigo");
                    } else {
                        System.out.println("Carril " + carril.getId() + " Corredor " + id + ": Me han despertado. Tengo el testigo");
                    }
                }
            }
            System.out.println("Carril " + carril.getId() + " Corredor " + id + ": Tengo el testigo y empiezo a correr");
            int fin = carril.thresholdCorredor(id);
            while (tieneTestigo) {
                int posicion = carril.obtenerPosicion(id);

                if (posicion == fin - 1 && id < 4) {
                    System.out.println("Carril " + carril.getId() + " Corredor " + id + ": He terminado de correr. Posición final: " + posicion);
                    tieneTestigo = false;
                } else if (posicion >= 399999) {
                    tieneTestigo = false;
                } else {
//                    if (carril.avanzarCorredor(id)) {
//                        Thread.sleep(10);
//                    }
                    carril.avanzarCorredor(id);
                }
            }
            Corredor siguiente = carril.puedePasarTestigo(id);
            if (siguiente != null) {
                System.out.println("Carril " + carril.getId() + " Corredor " + id + ": Pierdo el testigo");
                siguiente.recibirTestigo();
            } else {
                System.out.println("Carril " + carril.getId() + " Corredor " + id + ": No hay siguiente corredor...");
            }
            System.out.println("Carril " + carril.getId() + " : Despierto a todos");
            synchronized (carril) {
                carril.notifyAll();
            }
        } catch (InterruptedException e) {
            System.out.println("Carril " + carril.getId() + " Corredor " + id + ": Hilo interrumpido: " + e.getMessage());
        }
    }
}
