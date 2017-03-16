package fr.kata.infra;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 * Created by fmaury on 16/03/17.
 */
public class CsvLineSupplier implements Supplier<List<String>> {

    private final Scanner scanner;
    private List<String> currentLine = new ArrayList<>();

    public CsvLineSupplier(String filePath) throws FileNotFoundException {
        scanner = new Scanner(new File(filePath));
        scanner.useDelimiter(",");
    }


    @Override
    public List<String> get() {
        while(scanner.hasNext()){
            String next = scanner.next();
            if(next.contains("\n")){
                currentLine.add(next.split("\n")[0]);
                List<String> line = new ArrayList<>(currentLine);
                currentLine=new ArrayList<>();
                currentLine.add(next.split("\n")[1]);
                return line;
            }else{
                currentLine.add(next);
            }
        }
        return null;
    }
}
