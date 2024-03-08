package ex01;

public class PrinterBrocker {
    private boolean isPrinting = true;
    public synchronized void printHen(int counter) {
        for (int i = 0; i < counter; ++i) {
            try {
                while (isPrinting) {
                    super.wait();
                }
                System.out.println("Hen");
                isPrinting = true;
                super.notify();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }

    public synchronized void printEgg(int counter) {
        for (int i = 0; i < counter; ++i) {
            try {
                while(!isPrinting) {
                    super.wait();
                }
                System.out.println("Egg");
                isPrinting = false;
                super.notify();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
