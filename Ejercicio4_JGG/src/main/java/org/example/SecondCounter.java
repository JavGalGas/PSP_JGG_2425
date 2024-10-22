package org.example;

public class SecondCounter implements Runnable{
    private int timeCounter = 1;
    private boolean isRunning = true;
    int limit;

    public SecondCounter(int limit){
        this.limit = limit;
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                if (timeCounter <=5) {
                    System.out.println(timeCounter);
                }
                Thread.sleep(1000);
                timeCounter++;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void Stop(){isRunning=false;}
    public String getResult(){
        return timeCounter==limit ? "¡Lo has conseguido!" : "Vuelve a intentarlo, has detenido el contador en " + timeCounter + "...";
    }



}
