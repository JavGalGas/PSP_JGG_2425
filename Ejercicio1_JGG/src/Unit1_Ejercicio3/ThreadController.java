package Unit1_Ejercicio3;

import java.util.ArrayList;

public class ThreadController implements Runnable{
    private final ArrayList<Thread> threadList = new ArrayList<>();

    @Override
    public void run() {
        while(!threadList.isEmpty()) {
            try {
                for (Thread thread: threadList) {
                    if (!thread.isAlive()) {
                        removeThread(thread);
                        continue;
                    }
                    System.out.println(thread.getId() + " " + thread.getName() + " " + thread.getState());

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

    private void removeThread(Thread thread) {
        if (thread == null || !threadList.contains(thread)) {
            throw new NullPointerException();
        }
        threadList.remove(thread);
    }
}
