package Unit1_Ejercicio3;

import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;

public class ThreadController implements Runnable{
    private final ArrayList<Thread> threadList = new ArrayList<>();

    @Override
    public void run() {
        int terminatedThreads = 0;
        while(terminatedThreads < threadList.size()) {
            try {
                for (Thread thread: threadList) {
//                    if (thread.getState() == Thread.State.TERMINATED) {
//                        try{
//                            removeThread(thread);
//                        }
//                        catch (NullPointerException exception) {
//                            System.out.println(exception.getMessage());
//                        }
//                    }
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

    private void removeThread(Thread thread) throws NullPointerException{
        if (thread == null || !threadList.contains(thread)) {
            throw new NullPointerException();
        }
        threadList.remove(thread);
    }
}
