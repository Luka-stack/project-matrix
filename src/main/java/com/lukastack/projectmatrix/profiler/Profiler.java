package com.lukastack.projectmatrix.profiler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Profiler {

    private static final String ITERATIONS = "Iterations";
    private static final String AVERAGE = "AVG";
    private static final String MIN_MAX = "Min_Max";
    private static final String MODE = "Mode";
    private static final String STD = "Std";

    public Map<String, double[]> profileFunction(int warmups, int repeats, final ProfilerTimeUnit unit,
                                                 final ProfilerFunction profilerFunction) {

        if (warmups < 0 || repeats < 0) {
            throw new IllegalArgumentException("Warmups & repeats cannot be negative");
        }

        Map<String, double[]> result = new LinkedHashMap<>();

        iteration(warmups, unit, profilerFunction);
        var executionTimes = iteration(repeats, unit, profilerFunction);
        var statistics = getExecutionStatistics(executionTimes);

        result.put(ITERATIONS, executionTimes);
        result.put(AVERAGE, new double[]{ statistics[0] });
        result.put(MIN_MAX, new double[]{ statistics[2], statistics[1] });
        result.put(STD, new double[]{ statistics[3] });
        result.put(MODE, new double[]{ statistics[4] });

        return result;
    }

    public void profileFunctionToConsole(int warmups, int repeats, ProfilerTimeUnit unit,
                                         ProfilerFunction profilerFunction) {

        if (warmups < 0 || repeats < 0) {
            throw new IllegalArgumentException("Warmups & repeats cannot be negative");
        }

        explicitIteration("Warmup", warmups, unit, profilerFunction);
        var executionTimes = explicitIteration("Iteration", repeats, unit, profilerFunction);
        var statistics = getExecutionStatistics(executionTimes);

        System.out.printf("AVG: %f%n", statistics[0]);
        System.out.printf("Min: %f Max: %f%n", statistics[2], statistics[1]);
        System.out.printf("Std: %f%n", statistics[3]);
        System.out.printf("Mode: %f%n", statistics[4]);
    }

    public void profileFunctionToFile(int warmups, int repeats, ProfilerTimeUnit unit, String filename,
                                      ProfilerFunction profilerFunction) throws IOException {

        var mapInformation = profileFunction(warmups, repeats, unit, profilerFunction);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            int counter;

            if (warmups > 0) {
                writer.write("##### Warmups");
                writer.newLine();
                writer.write("# Completed "+ warmups +" warmups");
                writer.newLine();
                writer.newLine();
            }

            if (mapInformation.containsKey(ITERATIONS)) {
                writer.write("##### Iterations");
                writer.newLine();

                counter = 0;

                for (double time : mapInformation.get(ITERATIONS)) {
                    writer.write("# Iteration "+ counter +": "+ (long) time +" "+ unit.getName());
                    writer.newLine();

                    ++counter;
                }

                writer.newLine();
            }

            writer.write("##### Statistics");
            writer.newLine();
            writer.write("# Repeats: "+ repeats);
            writer.newLine();

            if (mapInformation.containsKey(AVERAGE)) {
                writer.write("# Average Time: "+ mapInformation.get(AVERAGE)[0] +" "+ unit.getName());
                writer.newLine();
            }

            if (mapInformation.containsKey(MIN_MAX)) {
                var minmax = mapInformation.get(MIN_MAX);
                writer.write("# Min Time: "+ minmax[1] +" "+ unit.getName());
                writer.newLine();
                writer.write("# Max Time: "+ minmax[0] +" "+ unit.getName());
                writer.newLine();
            }

            if (mapInformation.containsKey(STD)) {
                writer.write("# Std: "+ mapInformation.get(STD)[0] +" "+ unit.getName());
                writer.newLine();
            }

            if (mapInformation.containsKey(MODE)) {
                writer.write("# Mode Time: "+ mapInformation.get(MODE)[0] +" "+ unit.getName());
                writer.newLine();
            }

        }
        catch (IOException e) {
            throw new IOException(String.format("Error occurred while creating/opening file %s", filename), e);
        }
    }

    private double[] iteration(int repeats, final ProfilerTimeUnit unit, final ProfilerFunction profilerFunction) {

        double[] result = new double[repeats];
        long startTime;
        long endTime;

        for (int i = 0; i < repeats; ++i) {
            startTime = System.nanoTime();
            profilerFunction.profile();
            endTime = System.nanoTime();

            result[i] = unit.getTime(endTime - startTime);
        }

        return result;
    }

    private double[] explicitIteration(String information, int repeats, final ProfilerTimeUnit unit,
                                       final ProfilerFunction profilerFunction) {

        double[] result = new double[repeats];
        long startTime;
        long endTime;

        for (int i = 0; i < repeats; ++i) {
            startTime = System.nanoTime();
            profilerFunction.profile();
            endTime = System.nanoTime();

            result[i] = unit.getTime(endTime - startTime);
            System.out.printf("%s %d %d%n", information, i, (long) result[i]);
        }

        return result;
    }

    private double[] getExecutionStatistics(double[] executionTimes) {

        HashMap<Double, Integer> buckets = new HashMap<>();
        double maxKey = -1;
        double maxCount = 0;

        double sum = 0;
        double max = executionTimes[0];
        double min = executionTimes[1];

        for (double executionTime : executionTimes) {

            if (buckets.get(executionTime) != null) {
                int count = buckets.get(executionTime);
                buckets.put(executionTime, ++count);

                if (count > maxCount) {
                    maxCount = count;
                    maxKey = executionTime;
                }
            } else {
                buckets.put(executionTime, 1);
            }

            sum += executionTime;
            max = Math.max(max, executionTime);
            min = Math.min(min, executionTime);
        }

        double mean = sum / executionTimes.length;
        double std = 0.0;

        for (double executionTime : executionTimes) {
            std += Math.pow(executionTime - mean, 2);
        }

        return new double[]{ mean, max, min, Math.sqrt(std), maxKey};
    }
}
