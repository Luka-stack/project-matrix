package com.lukastack.projectmatrix.profiler;

public enum ProfilerTimeUnit {
    NANOSECONDS("ns", nanoTime -> nanoTime),
    MICROSECONDS("µsec", nanoTime -> (nanoTime / Math.pow(10, 4))),
    MILLISECONDS("ms", nanoTime -> (nanoTime / Math.pow(10, 6))),
    SECONDS("s", nanoTime -> (nanoTime / Math.pow(10, 9))),
    MINUTES("mins", nanoTime -> (nanoTime / (6 * Math.pow(10, 10))));

    private final String name;
    private final ProfilerUnitConverter converter;

    ProfilerTimeUnit(String name, ProfilerUnitConverter converter) {

        this.name = name;
        this.converter = converter;
    }

    public double getTime(long nanoTime) {

        return converter.convert(nanoTime);
    }

    public String getName() {

        return name;
    }
}
