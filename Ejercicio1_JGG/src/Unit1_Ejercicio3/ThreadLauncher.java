package Unit1_Ejercicio3;

import Unit1_Ejercicio2.PrimeThread;

public class ThreadLauncher {
    public void launch(int maxThreads) {
        for (int i = 0; i < maxThreads; i++){
            Unit1_Ejercicio2.PrimeThread pt = new PrimeThread();
            pt.setName("Hilo " + (i+1));
            Thread thread = new Thread(pt);
            thread.start();
        }
    }
}
