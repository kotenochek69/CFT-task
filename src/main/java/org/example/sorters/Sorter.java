package org.example.sorters;

import java.io.IOException;

public interface Sorter {
    void setOutputFile(String outputFile);
    void setInputFiles(String [] inputFile);

    void mergeSort() throws IOException;

    void setOrder(String comparator);
}
