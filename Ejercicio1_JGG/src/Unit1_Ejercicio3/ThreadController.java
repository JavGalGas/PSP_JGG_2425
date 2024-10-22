package Unit1_Ejercicio3;

import java.util.ArrayList;

public class ThreadController implements Runnable{
    private final ArrayList<Thread> threadList = new ArrayList<>();

    @Override
    public void run() {
        int terminatedThreads = 0;
        while(terminatedThreads < threadList.size()) {
            try {
                for (Thread thread: threadList) {
                    System.out.println(thread.getId() + " " + thread.getName() + " " + thread.getState());
                    if(thread.getState() == Thread.State.TERMINATED) {
                        terminatedThreads++;
                    }
                }
                Thread.sleep(1000);
            }
            catch (InterruptedException exception) {
                System.out.println("Thread has been interrupted.");
            }
        }
    }

    public void addThread(Thread thread) {
        if (thread == null)
            throw new NullPointerException();
        threadList.add(thread);
    }
}
