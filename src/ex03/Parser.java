package ex03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    private static final String linkFilePath = "files_urls.txt";

    public static ArrayList<String> getLinks() {
        ArrayList<String> linkList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(linkFilePath))) {
            String buffer;
            while ((buffer = bufferedReader.readLine()) != null) {
                linkList.add(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linkList;
    }
}
