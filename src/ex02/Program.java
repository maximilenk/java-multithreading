package ex02;

import static java.lang.System.err;
import static java.lang.System.exit;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2 || !args[0].startsWith("--arraySize=")
            || !args[1].startsWith("--threadsCount=")) {
            err.println("wrong arguments");
            exit(-1);
        }
        String sizeString = args[0].trim().replace("--arraySize=", "");
        String threadCounter = args[1].trim().replace("--threadsCount=", "");
        int counter = 0;
        int size = 0;
        try {
            counter = Integer.parseInt(threadCounter);
            size = Integer.parseInt(sizeString);
            if (counter <= 0 || size <= 0) {
                err.println("Counter and size can be only positive numbers");
                exit(-1);
            }
            if (size > 2000000 || counter > size) {
                err.println("wrong numbers");
                exit(-1);
            }
        } catch (Exception e) {
            System.out.println("'" + sizeString + "' or '" 
                + threadCounter + "' impossible to convert to number");
        }

        Random r = new Random();
        int[] numbers = r.ints(-1000, 1001).limit(size).toArray();
        System.out.println(IntStream.of(numbers).sum());
        
        ArrayList<Thread> list = makeThread(numbers, size, counter);
        for (Thread t : list) {
            t.start();
        }
        try {
            for (Thread t : list) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.getStackTrace();
        }
        System.out.println("Sum by threads: " + SumFinder.getSum());
    }

    private static ArrayList<Thread> makeThread(int[] numbers, int size, int threadCounter) {
        ArrayList<Thread> list = new ArrayList<>();
        int fromCommon = 0;
        for (int i = 0; i < threadCounter; ++i) {
            int interval = size / threadCounter;
            int from = fromCommon;
            int to = (i == threadCounter - 1) ? (size - 1) : from + interval - 1;
            fromCommon = to + 1;
            Thread thread = new Thread(new SumFinder(from, to, numbers, i + 1));
            list.add(thread); 

        }
        return list;
    }
}