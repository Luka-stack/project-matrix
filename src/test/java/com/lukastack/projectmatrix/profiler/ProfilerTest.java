package com.lukastack.projectmatrix.profiler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.stream.DoubleStream;

class ProfilerTest {

    Profiler profiler;

    @BeforeEach
    void setUp() {
        profiler = new Profiler();
    }

    @Test
    void profileFunction_returnsAllInformation() {

        double[] test1 = DoubleStream.iterate(0, n -> n + 100).limit(10000).toArray();
        double[] test2 = DoubleStream.iterate(0, n -> n + 500).limit(10000).toArray();

        var result = profiler.profileFunction(
                0,
                5,
                ProfilerTimeUnit.SECONDS,
                () -> {
                    double total = 0.0;
                    for (int i = 0; i < test1.length; ++i) {
                        for (int j = 0; j < test2.length; ++j) {
                            total += test1[i] * test2[j];
                        }
                    }
                }
        );

        Assertions.assertTrue(result.containsKey("Iterations"));
        Assertions.assertTrue(result.containsKey("AVG"));
        Assertions.assertTrue(result.containsKey("Min_Max"));
        Assertions.assertTrue(result.containsKey("Std"));
        Assertions.assertTrue(result.containsKey("Mode"));
        Assertions.assertEquals(5, result.get("Iterations").length);
    }

    @Test
    void profileFunctionToFile_fileCreated() throws IOException {

        String filename = "testFileExists.txt";

        double[] test1 = DoubleStream.iterate(0, n -> n + 100).limit(10000).toArray();
        double[] test2 = DoubleStream.iterate(0, n -> n + 500).limit(10000).toArray();

        profiler.profileFunctionToFile(
                0,
                5,
                ProfilerTimeUnit.NANOSECONDS,
                "testFileExists.txt",
                () -> {
                    double total = 0.0;
                    for (int i = 0; i < test1.length; ++i) {
                        for (int j = 0; j < test2.length; ++j) {
                            total += test1[i] * test2[j];
                        }
                    }
                }
        );

        File profilerFile = new File(filename);
        Assertions.assertTrue(profilerFile.exists());
    }
}