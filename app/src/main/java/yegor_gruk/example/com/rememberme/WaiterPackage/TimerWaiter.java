package yegor_gruk.example.com.rememberme.WaiterPackage;

public abstract class TimerWaiter implements Notifier {

    private LikeStack<StackElement> stack;

    private long delay;

    public TimerWaiter(long delay) {
        this.delay = delay;
        stack = new LikeStack<>();
    }

    public void runStartThread() {

        stack.push(StackElement.START);

        new Thread(new MyTask(StackElement.START, delay, this)).start();
    }

    public void runStopThread() {

        stack.push(StackElement.STOP);

        new Thread(new MyTask(StackElement.STOP, delay, this)).start();
    }

    @Override
    public void notifyThreadStop(StackElement key) {

        switch (key) {
            case START:
                stack.pop(StackElement.START);
                break;
            case STOP:
                stack.pop(StackElement.STOP);
                break;
        }

        if (stack.isEmpty() && stack.isSymmetric()) {
            doOnExit();
        }

    }

    public abstract void doOnExit();

}