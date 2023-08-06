package org.example.sorters;

import lombok.extern.java.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Log
public class StringSorter implements Sorter{
    private String outputFile;
    private final List<String> inputFiles = new ArrayList<>();

    private String nextValue;
    private Comparator<String> comparator;

    @Override
    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public void setInputFiles(String[] inputFiles) {
        this.inputFiles.addAll(Arrays.asList(inputFiles));
    }

    @Override
    public void mergeSort() throws IOException {
        log.info("Started sorting lines as strings: "+inputFiles);
        log.info("All results will be written in: "+outputFile);
        int count = inputFiles.size();
        File outFile = new File(outputFile);
        FileWriter writer = new FileWriter(outFile, false);
        ArrayList<String> newList = new ArrayList<>();
        ArrayList<Scanner> scanners = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            File inputStream = new File(inputFiles.get(i));
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter(System.getProperty("line.separator"));
            scanners.add(i, scanner);
            newList.add(i, scanner.nextLine());
        }
        while (count >= 1) {
            int ind = FindIndAndNext(newList);
            writer.write(nextValue+"\n");
            if (!scanners.get(ind).hasNextLine()) {
                log.info("End of "+inputFiles.get(ind)+" file");
                count--;
                inputFiles.remove(ind);
                scanners.remove(ind);
                newList.remove(ind);
            }
            else{
                newList.set(ind,scanners.get(ind).nextLine());
            }
            writer.flush();
        }
        log.info("Finished sorting");
    }

    @Override
    public void setOrder(String comparator) {
        if (Objects.equals(comparator, "-d")){
            log.info("Sorting in descending order.");
            this.comparator = Comparator.reverseOrder();
            return;
        }
        if (Objects.equals(comparator, "-a")){
            log.info("Sorting in ascending order.");
            this.comparator = Comparator.naturalOrder();
            return;
        }
        throw new RuntimeException("Wrong sorting order:"+ comparator);
    }

    private int FindIndAndNext(ArrayList<String> list) {
        nextValue = list.stream().min(comparator).get();
        return list.indexOf(nextValue);
    }
}
