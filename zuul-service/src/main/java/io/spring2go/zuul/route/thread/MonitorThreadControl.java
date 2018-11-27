package io.spring2go.zuul.route.thread;

public class MonitorThreadControl {
    
    public static int state = 0;
    public static Object objLock = new Object();
    
    public static int getState() {
        synchronized (objLock) {
            if (state == 0) {
                try {
                    objLock.wait(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return state;
        }
    }
    
    public static void setState(int n) {
        synchronized (objLock) {
            state = n;
            objLock.notifyAll();
        }
    }

}
