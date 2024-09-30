package Unit1_Ejercicio2;

public class ThreadLauncher {
    public void launch(int maxThreads) {
        for (int i = 0; i < maxThreads; i++){
            PrimeThread pt = new PrimeThread();
            pt.setName("Hilo " + (i+1));
            Thread thread = new Thread(pt);
            thread.start();
        }
    }
}
