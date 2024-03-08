package ex01;

import static java.lang.System.err;
import static java.lang.System.exit;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--count=")) {
            err.println("wrong arguments");
            exit(-1);
        }
        String counterString = args[0].trim().replace("--count=", "");
        int counter = 0;
        try {
            counter = Integer.parseInt(counterString);
            if (counter < 0) {
                err.println("Counter can`t be a negative number");
                exit(-1);
            }
        } catch (Exception e) {
            System.out.println("'" + counterString + "' impossible to convert to number");
        }
        final int count = counter;
        PrinterBrocker pb = new PrinterBrocker();
        Thread eggThread = new Thread(new Runnable() {
            @Override
            public void run() {
                pb.printEgg(count);
            }
        });

        Thread henThread = new Thread(new Runnable() {
            @Override
            public void run() {
                pb.printHen(count);
            }
        });

        eggThread.start();
        henThread.start();

        try {
            eggThread.join();
            henThread.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }
}