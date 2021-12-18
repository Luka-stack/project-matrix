package com.lukastack.projectmatrix.profiler;

@FunctionalInterface
interface ProfilerUnitConverter {

    double convert(long time);
}
