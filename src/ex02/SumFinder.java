package ex02;

public class SumFinder implements Runnable {
    private static int sum;
    private static final Object forSync = new Object();
    private int sumOfThread;
    private final int from;
    private final int to;
    private final int threadIndex;
    private int[] array;

    public SumFinder(int from, int to, int[] array, int threadIndex) {
        sum = 0;
        this.from = from;
        this.to = to;
        this.array = array;
        sumOfThread = 0;
        this.threadIndex = threadIndex;
    }

    public void findSum() {
        for (int i = from; i <= to; ++i) {
            sumOfThread += array[i];  
        }
        synchronized(forSync) {
            sum += sumOfThread;
        }
    }

    @Override
    public void run() {
        findSum();
        System.out.println(toString());
    }

    public int getSumOfThread() {
        return sumOfThread;
    }

    public static int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "Thread " + threadIndex
            + ": from " + from + " to "
            + to + " sum is " + sumOfThread;
    }
}
