package ex03;

import static java.lang.System.err;
import static java.lang.System.exit;
import java.util.ArrayList;

public class Program {
    public static void main(String... args) {
        if (args.length != 1 || !args[0].startsWith("--threadsCount=")) {
            err.println("wrong arguments");
            exit(-1);
        }
        String counterString = args[0].trim().replace("--threadsCount=", "");
        int counter = 0;
        try {
            counter = Integer.parseInt(counterString);
            if (counter <= 0) {
                err.println("Counter must be a positive number");
                exit(-1);
            }
        } catch (Exception e) {
            err.println("'" + counterString + "' impossible to convert to number");
            exit(-1);
        }
        ArrayList<String> linkList = Parser.getLinks();
        ArrayList<Thread> threads = makeThreads(counter, linkList);
        for (Thread i : threads) {
            i.start();
        }
        try {
            for (Thread i : threads) {
                i.join();
            } 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

    private static ArrayList<Thread> makeThreads(int counter, ArrayList<String> linkList) {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 1; i <= counter; ++i) {
            threads.add(new Thread(new Downloder(linkList, i)));
        }
        return threads;
    }
}
