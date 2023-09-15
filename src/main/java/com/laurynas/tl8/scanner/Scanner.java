package com.laurynas.tl8.scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scanner {
    private int index;
    private final List<Character> fileContent;

    public Scanner(String filePath) {
        index = 0;
        fileContent = new ArrayList<>();
        try (BufferedReader fileInput = new BufferedReader(new FileReader(filePath))) {
            String s;
            while ((s = fileInput.readLine()) != null) {
                for (Character c : s.toCharArray()) {
                    fileContent.add(c);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public char next() {
        return fileContent.get(index++);
    }

    public char peek() {
        return fileContent.get(index);
    }

    public void error(String message) {
        System.err.println(message);
        System.exit(0);
    }

    public boolean eof() {
        return index >= fileContent.size();
    }

}
