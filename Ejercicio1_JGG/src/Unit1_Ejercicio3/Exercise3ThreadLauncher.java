package Unit1_Ejercicio3;
public class Exercise3ThreadLauncher {
    public void launch(int maxThreads) {
        ThreadController threadController = new ThreadController();
        for (int i = 0; i < maxThreads; i++){
            Exercise3PrimeThread pt = new Exercise3PrimeThread();
            pt.setName("Hilo " + (i+1));
            Thread thread = new Thread(pt);
            thread.setName("Hilo " + (i+1));
            threadController.addThread(thread);
            thread.start();
        }
        Thread controllerThread = new Thread(threadController);
        controllerThread.start();
    }
}
