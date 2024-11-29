package org.example;

public class FabricarCarroceria implements Runnable {
    private Vehiculo vehiculo;

    public FabricarCarroceria(Vehiculo vehiculo){
        this.vehiculo = vehiculo;
    }

    @Override
    public void run() {
        System.out.println("FC: Fabricando carrocería");
        try {
            Thread.sleep((long)((Math.random()*9000)+1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Carroceria c = new Carroceria();
        System.out.println("FC: Carrocería fabricada");

        synchronized(vehiculo){
            System.out.println("FM: Ensamblando carrocería");
            vehiculo.ensamblarCarroceria(c);
            vehiculo.notifyAll();
        }

    }
}
