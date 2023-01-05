package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

     private synchronized String getContent(Predicate<Integer> filer) throws IOException {
         try (InputStream i = new FileInputStream(file)) {
             String output = "";
             int data;
             while ((data = i.read()) > 0) {
                 if (filer.test(data)) {
                     output += (char) data;
                 }
             }
             return output;
         }
     }

    public synchronized String getContent() throws IOException {
        return getContent(data -> true);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return getContent(data -> data < 128);
    }
}
