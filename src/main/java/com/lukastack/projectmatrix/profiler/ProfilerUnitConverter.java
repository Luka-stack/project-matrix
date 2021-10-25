package com.lukastack.projectmatrix.profiler;

@FunctionalInterface
interface ProfilerUnitConverter {

    long convert(long time);
}
