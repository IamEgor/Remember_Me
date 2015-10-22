package yegor_gruk.example.com.rememberme.WaiterPackage;

public class MyTask implements Runnable {

    public Thread thread;
    private long delay;
    private StackElement name;
    private Notifier notifier;

    MyTask(StackElement name, long delay, Notifier notifier) {
        this.name = name;
        this.delay = delay;
        this.notifier = notifier;
    }

    public void start() {

        if (thread == null) {
            thread = new Thread(this, name.name());
            thread.start();
        }

    }

    @Override
    public void run() {

        try {

            Thread.sleep(delay);

        } catch (InterruptedException e) {

            e.printStackTrace();

        } finally {

            notifier.notifyThreadStop(name);

        }
    }

}