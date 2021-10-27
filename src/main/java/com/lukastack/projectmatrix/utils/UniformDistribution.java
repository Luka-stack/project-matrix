package com.lukastack.projectmatrix.utils;

import java.util.Random;

public class UniformDistribution {

    private static volatile long seedUniquifier = 8682522807148012L;

    public static double getRandom() {
        return new Random(seedUniquifier + System.nanoTime()).nextDouble();
    }

    private UniformDistribution() {
        throw new UnsupportedOperationException("Utility class. Should not be instantiated");
    }
}
