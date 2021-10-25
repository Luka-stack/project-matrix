package com.lukastack.projectmatrix.utils;

import java.util.Random;

public class NormalDistribution {

    private static volatile long seedUniquifier = 9678522753148012L;

    public static double getRandom() { return new Random(seedUniquifier + System.nanoTime()).nextGaussian(); }

    private NormalDistribution() { throw new IllegalStateException("Utility class"); }
}
