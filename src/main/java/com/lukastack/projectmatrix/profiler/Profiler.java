package com.lukastack.projectmatrix.profiler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Profiler {

    public Map<String, double[]> profileFunction(int warmups, int repeats, final ProfilerTimeUnit unit,
                                                 final ProfilerFunction profilerFunction) {

        Map<String, double[]> result = new LinkedHashMap<>();

        iteration(warmups, unit, profilerFunction);
        var executionTimes = iteration(repeats, unit, profilerFunction);
        var statistics = getExecutionStatistics(executionTimes);

        result.put("Iterations", executionTimes);
        result.put("AVG", new double[]{ statistics[0] });
        result.put("MaxMin", new double[]{ statistics[1], statistics[2] });
        result.put("Mode", new double[]{ statistics[3] });

        return result;
    }

    public void profileFunctionToConsole(int warmups, int repeats, ProfilerTimeUnit unit,
                                       ProfilerFunction profilerFunction) {

        explicitIteration("Warmup", warmups, unit, profilerFunction);
        var executionTimes = explicitIteration("Iteration", repeats, unit, profilerFunction);
        var statistics = getExecutionStatistics(executionTimes);

        System.out.printf("AVG: %f%n", statistics[0]);
        System.out.printf("Max: %f Min: %f%n", statistics[1], statistics[2]);
        System.out.printf("Mode: %f%n", statistics[3]);
    }

    public void profileFunctionToFile(int warmups, int repeats, ProfilerTimeUnit unit, String filename,
                                        ProfilerFunction profilerFunction) {

        var mapInformation = profileFunction(warmups, repeats, unit, profilerFunction);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            int counter;

            if (mapInformation.containsKey("Warmup")) {
                writer.write("# Warmups");
                writer.newLine();

                counter = 0;

                for (double time : mapInformation.get("Warmup")) {
                    writer.write("Warmup #"+ counter +": "+ (long) time +" "+ unit.getName());
                    writer.newLine();
                }
            }

            if (mapInformation.containsKey("Iteration")) {
                writer.write("# Iterations");
                writer.newLine();

                counter = 0;

                for (double time : mapInformation.get("Iteration")) {
                    writer.write("Iteration #"+ counter +": "+ (long) time +" "+ unit.getName());
                    writer.newLine();
                }
            }

            writer.write("# Statistics");
            writer.newLine();
            writer.write("# Repeats: "+ repeats);
            writer.newLine();

            if (mapInformation.containsKey("AVG")) {
                writer.write("# Average Time: "+ mapInformation.get("AVG")[0] +" "+ unit.getName());
            }

            if (mapInformation.containsKey("MinMax")) {
                var minmax = mapInformation.get("MinMax");
                writer.write("# Min Time: "+ minmax[0] +" Max Time: "+ minmax[1] +" "+ unit.getName());
            }

            if (mapInformation.containsKey("Mode")) {
                writer.write("# Mode Time: "+ mapInformation.get("Mode")[0] +" "+ unit.getName());
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("File Writer Error");
        }
    }

    private double[] iteration(int repeats, final ProfilerTimeUnit unit, final ProfilerFunction profilerFunction) {

        double[] result = new double[repeats];
        long startTime;
        long endTime;

        for (int i = 0; i < repeats; ++i) {
            startTime = System.nanoTime();
            profilerFunction.test();
            endTime = System.nanoTime();

            result[i] = unit.getTime(endTime - startTime);
        }

        return result;
    }

    private double[] explicitIteration(String information, int repeats, final ProfilerTimeUnit unit, final ProfilerFunction profilerFunction) {

        double[] result = new double[repeats];
        long startTime;
        long endTime;

        for (int i = 0; i < repeats; ++i) {
            startTime = System.nanoTime();
            profilerFunction.test();
            endTime = System.nanoTime();

            result[i] = unit.getTime(endTime - startTime);
            System.out.printf("%s #%d %d%n", information, i, (long) result[i]);
        }

        return result;
    }

    private double[] getExecutionStatistics(double[] executionTimes) {

        HashMap<Double, Integer> buckets = new HashMap<>();
        double maxKey = 0;
        double maxCount = 0;

        double avg = 0;
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

            avg += executionTime;
            max = Math.max(max, executionTime);
            min = Math.min(min, executionTime);
        }

        return new double[]{ avg / executionTimes.length, max, min, maxKey };
    }

    // asString -> do asMap and convert to String
    // toConsole -> print every iteration + stats at the end

    // iteration
}
