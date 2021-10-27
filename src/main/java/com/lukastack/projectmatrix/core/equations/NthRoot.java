package com.lukastack.projectmatrix.core.equations;

public class NthRoot {

    private NthRoot() {
        throw new UnsupportedOperationException("Utility class. Should not be instantiated");
    }

    public static double nthRoot(double num, double n) {
        return Math.pow(num, 1.0 / n);
    }
}
