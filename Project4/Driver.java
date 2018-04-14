import java.util.*;
import java.io.*;

public class Driver {
    public static void main(String args[]) {
        int arraySize=10;
        int trials=1;
        String fileName;

        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the size of arrays");
        arraySize = input.nextInt();
        System.out.println("Please enter the number of trials");
        trials = input.nextInt();
        System.out.println("Please enter the name of output file");
        fileName = input.next();

        //arraySize = getInt("Please enter the size of arrays");
        //trials = getInt("Please enter the number of trials");

        String algoName[] = {"Simple QuickSort",
                             "Median of Three (5)",
                             "Median Of Three (20)",
                             "Median Of Three (100)",
                             "Random Pivot (5)",
                             "Iterative QuickSort",
                             "Recursive MergeSort",
                             "Iterative MergeSort",
                             "Iterative MergeSort with Explicit Stack",
                             "Insertion Sort",
                             "Shell Sort"};

        String order[] = {"Random",
                          "Sorted",
                          "Reverse"};

        long r[];

        long randTime[] = new long[algoName.length];
        long randComp[] = new long[algoName.length];
        long randMove[] = new long[algoName.length];
        long sortedTime[] = new long[algoName.length];
        long sortedComp[] = new long[algoName.length];
        long sortedMove[] = new long[algoName.length];
        long reverseTime[] = new long[algoName.length];
        long reverseComp[] = new long[algoName.length];
        long reverseMove[] = new long[algoName.length];

        long randStat[][] = {randTime, randComp, randMove};
        long sortedStat[][] = {sortedTime, sortedComp, sortedMove};
        long reverseStat[][] = {reverseTime, reverseComp, reverseMove};

        long stat[][][] = {randStat, sortedStat, reverseStat};

        Integer rand[] = {0};
        Integer sorted[] = generateSortedArray(arraySize);
        Integer reverse[] = generateReverseSortedArray(arraySize);

        Integer array[][] = {rand, sorted, reverse};

        for (int i = 0; i < order.length; i++) {
            for (int _i = 0; _i < trials; _i++) {
                if (i == 0) {
                    array[i] = generateRandomArray(arraySize);
                }
                for (int j = 0; j < algoName.length; j++) {
                    if ((j == 0 || j == 9) && arraySize > 100000) {
                        continue;
                    }
                    r = sortAlgo(algoName[j], array[i], arraySize, order[i]);
                    for (int k = 0; k < 3; k++) {
                        stat[i][k][j] += r[k];
                    }
                }
            }
        }

        fileOutput(fileName, arraySize, trials, algoName, order, stat);

    }

    private static void traceOutputMode(int arraySize, Integer array[], Integer temp[], String algo, String order) {
        if (arraySize <= 20) {
            System.out.println("Algorithm: "+algo);
            System.out.println("Array Size: "+arraySize);
            System.out.println("Order: "+order);
            System.out.println(Arrays.toString(array));
            System.out.println(Arrays.toString(temp));
            System.out.println();
        }
    }

    private static void fileOutput(String fileName, int arraySize, int trials, String algoName[], String order[], long stat[][][]) {
        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            float t, c, m;
            for (int i = 0; i < order.length; i++) {
                for (int j = 0; j < algoName.length; j++) {
                    if ((j == 0 || j == 9) && arraySize > 100000) {
                        continue;
                    }
                    writer.println("Algorithm: "+algoName[j]);
                    writer.println("Array Size: "+arraySize);
                    writer.println("Order: "+order[i]);
                    writer.println("Number of trials: "+trials);
                    t = ((float)stat[i][0][j]/1000000000)/trials;
                    c = (float)stat[i][1][j]/trials;
                    m = (float)stat[i][2][j]/trials;
                    writer.println("Average Time: "+t+" sec");
                    writer.println("Average number of comparisons: "+c+" comparisons");
                    writer.println("Average number of data moves: "+m+" moves");
                    writer.println();
                }
            }
            writer.close();
        }
        catch (IOException ex) {
            return;
        }
    }

    private static long[] sortAlgo(String algoName, Integer array[], int arraySize, String order) {
        long[] result = new long[3];
        Integer[] temp = new Integer[arraySize];
        System.arraycopy(array, 0, temp, 0, arraySize);
        TextMergeQuick tmq = new TextMergeQuick();
        switch (algoName) {
            case "Simple QuickSort":
                tmq.simpleQuickSort(temp, arraySize);
                break;
            case "Median of Three (5)":
                tmq.setMinSize(5);
                tmq.quickSort(temp, arraySize);
                break;
            case "Median Of Three (20)":
                tmq.setMinSize(20);
                tmq.quickSort(temp, arraySize);
                break;
            case "Median Of Three (100)":
                tmq.setMinSize(100);
                tmq.quickSort(temp, arraySize);
                break;
            case "Random Pivot (5)":
                tmq.randomPivotQuickSort(temp, arraySize);
                break;
            case "Iterative QuickSort":
                tmq.iterativeQuickSort(temp, arraySize);
                break;
            case "Recursive MergeSort":
                tmq.mergeSort(temp, arraySize);
                break;
            case "Iterative MergeSort":
                tmq.iterativeMergeSort(temp, arraySize);
                break;
            case "Iterative MergeSort with Explicit Stack":
                tmq.iterativeMergeSortwithExplicitStack(temp, arraySize);
                break;
            case "Insertion Sort":
                tmq.insertionSort(temp, arraySize);
                break;
            case "Shell Sort":
                tmq.shellSort(temp, arraySize);
                break;
        }
        result[0] = tmq.getTime();
        result[1] = tmq.getComparisons();
        result[2] = tmq.getMoves();
        traceOutputMode(arraySize, array, temp, algoName, order);
        return result;
    }

    private static Integer[] generateRandomArray(int size) {
        Integer result[] = new Integer[size];
        Random generator = new Random();

        for(int i = 0; i < size; i++) {
            int value = generator.nextInt(size);
            result[i] = new Integer(value);
        }

        return result;
    }

    private static Integer[] generateSortedArray(int size) {
        Integer result[] = new Integer[size];

        for(int i = 0; i < size; i++) {
            result[i] = new Integer(i+1);
        }

        return result;
    }

    private static Integer[] generateReverseSortedArray(int size) {
        Integer result[] = new Integer[size];

        for(int i = 0; i < size; i++) {
            result[i] = new Integer(size-i);
        }

        return result;
    }
}
