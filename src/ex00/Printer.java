package ex00;

public final class Printer implements Runnable {
    private final String forPrint;
    private final int counter;

    public Printer(final String forPrint, final int counter) {
        this.forPrint = forPrint;
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < counter; ++i) {
            System.out.println(forPrint);
        }
    }
}
