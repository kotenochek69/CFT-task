package org.example;

import org.example.sorters.IntegerSorter;
import org.example.sorters.Sorter;
import org.example.sorters.StringSorter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Main {
    public static Map<String, Sorter> sorters = new HashMap<>();
    private static Sorter sorter;

    public static void main(String[] args) {
        ValidateArgs(args);
        AddSorters();
        InitSorter(args);
        try {
            sorter.mergeSort();
        } catch (Exception e) {
            throw new RuntimeException("Failed to sort: "+e.getMessage());
        }
    }

    private static void InitSorter(String[] args) {
        sorter = sorters.get(args[0]);
        if (sorter == null){
            throw new RuntimeException("Failed to get sorter for flag: "+ args[0]);
        }
        int i = 0;
        if (Objects.equals(args[1],"-a") || Objects.equals(args[2], "-d")) {
            sorter.setOrder(args[1]);
        } else {
            sorter.setOrder("-a");
            i++;
        }
        SetSorterFiles(args, i);
    }

    private static void SetSorterFiles(String[] args, int i) {
        sorter.setOutputFile(args[2- i]);
        String [] sorterArgs = Arrays.copyOfRange(args, 3- i, args.length);
        sorter.setInputFiles(sorterArgs);
    }

    private static void AddSorters() {
        sorters.put("-i",new IntegerSorter());
        sorters.put("-s",new StringSorter());
    }

    private static void ValidateArgs(String[] args) {
        if (args.length < 5){
            System.out.println("Usage: sort-it.exe [-i|-s] [-a|-d] [OUTPUT FILE] [INPUT 1], [INPUT2]...");
            throw new IllegalArgumentException("Insufficient arguments");
        }
    }
}