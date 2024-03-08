package ex03;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class Downloder implements Runnable {
    private final ArrayList<String> fileLink;
    private final int threadIndex;
    private final String OUTPUT_MESSAGE_FORMAT = "Thread-%d %s download file number %d\n";
    private static int nextIndex;
    private static final Object forSync = new Object();

    public Downloder(ArrayList<String> fileLink, int threadIndex) {
        this.fileLink = fileLink;
        this.threadIndex = threadIndex;
        nextIndex = 0;
    }
    
    private int getNextIndex() {
        synchronized(forSync) {
            return nextIndex++;
        }
    }

    @Override
    public void run() {
        int index = getNextIndex();
        while (index < fileLink.size()) {
            download(index);
            index = getNextIndex();
        }
    }

    private void download(int fileIndex) {
        System.out.printf(OUTPUT_MESSAGE_FORMAT, threadIndex, "start", fileIndex + 1);
        try (InputStream in = new URL(fileLink.get(fileIndex)).openStream()) {
            Files.copy(in, Paths.get(getFileNameFromURL(fileIndex)), StandardCopyOption.REPLACE_EXISTING);
            System.out.printf(OUTPUT_MESSAGE_FORMAT, threadIndex, "finish", fileIndex + 1);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("file " + (fileIndex + 1) + " wrong URL");
        }
    }

    private String getFileNameFromURL(int fileIndex) {
        String result;
        String[] subString = fileLink.get(fileIndex).split("/");
        result = subString[subString.length - 1];
        return result;
    } 

}
