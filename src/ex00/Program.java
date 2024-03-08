package ex00;

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

        Printer Egg = new Printer("Egg", counter);
        Thread thread1 = new Thread(Egg);

        Printer Hen = new Printer("Hen", counter);
        Thread thread2 = new Thread(Hen);

        thread1.start();
        thread2.start();
        try { 
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.getStackTrace();
        }

        Printer Human = new Printer("Human", counter);
        Human.run();
    }
}