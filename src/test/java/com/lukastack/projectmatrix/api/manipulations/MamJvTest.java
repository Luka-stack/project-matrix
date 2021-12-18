package com.lukastack.projectmatrix.api.manipulations;

import com.lukastack.projectmatrix.core.matrices.LiMatJv;
import com.lukastack.projectmatrix.errors.DimensionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MamJvTest {

    @Test
    void uniformDistribution() {

        int rows = 5;
        int cols = 5;

        var matrix = MamJv.uniformDistribution(5, 5);
        var shape = matrix.shape();

        Assertions.assertEquals(rows, shape[0]);
        Assertions.assertEquals(cols, shape[1]);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                Assertions.assertTrue(0 < matrix.get(i, j) && matrix.get(i, j) < 1);
            }
        }
    }

    @Test
    void normalDistribution() {

        int rows = 5;
        int cols = 5;

        var matrix = MamJv.normalDistribution(5, 5);
        var shape = matrix.shape();

        Assertions.assertEquals(rows, shape[0]);
        Assertions.assertEquals(cols, shape[1]);
    }

    @Test
    void zeros() {

        int rows = 5;
        int cols = 5;

        var matrix = MamJv.zeros(rows, cols);
        var shape = matrix.shape();

        Assertions.assertEquals(rows, shape[0]);
        Assertions.assertEquals(cols, shape[1]);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                Assertions.assertEquals(0, matrix.get(i, j));
            }
        }
    }

    @Test
    void zeros_LiMatJv() {

        int rows = 5;
        int cols = 5;

        var matrix = MamJv.zeros(rows, cols, LiMatJv.class);
        var shape = matrix.shape();

        Assertions.assertEquals(rows, shape[0]);
        Assertions.assertEquals(cols, shape[1]);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                Assertions.assertEquals(0, matrix.get(i, j));
            }
        }
    }

    @Test
    void ones() {

        int rows = 5;
        int cols = 5;

        var matrix = MamJv.ones(rows, cols);
        var shape = matrix.shape();

        Assertions.assertEquals(rows, shape[0]);
        Assertions.assertEquals(cols, shape[1]);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                Assertions.assertEquals(1, matrix.get(i, j));
            }
        }
    }

    @Test
    void identity() {

        int size = 3;

        var matrix = MamJv.identity(size);
        var shape = matrix.shape();

        Assertions.assertEquals(size, shape[0]);
        Assertions.assertEquals(size, shape[1]);

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                Assertions.assertEquals(i == j ? 1 : 0, matrix.get(i, j));
            }
        }
    }

    @Test
    void withValues() {

        int rows = 5;
        int cols = 5;
        double val = 2.532;

        var matrix = MamJv.withValue(rows, cols, val);
        var shape = matrix.shape();

        Assertions.assertEquals(rows, shape[0]);
        Assertions.assertEquals(cols, shape[1]);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                Assertions.assertEquals(val, matrix.get(i, j));
            }
        }
    }

    @Test
    void fromRange() {

        int rows = 5;
        int cols = 5;
        double val = 0;

        var matrix = MamJv.fromRange(rows, cols);
        var shape = matrix.shape();

        Assertions.assertEquals(rows, shape[0]);
        Assertions.assertEquals(cols, shape[1]);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j, val += 1) {
                Assertions.assertEquals(val, matrix.get(i, j));
            }
        }
    }

    @Test
    void fromRange_withStart() {

        int rows = 5;
        int cols = 5;
        double val = 5.5;

        var matrix = MamJv.fromRange(rows, cols, val);
        var shape = matrix.shape();

        Assertions.assertEquals(rows, shape[0]);
        Assertions.assertEquals(cols, shape[1]);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j, val += 1) {
                Assertions.assertEquals(val, matrix.get(i, j));
            }
        }
    }

    @Test
    void fromRange_withStartAndStep() {

        int rows = 5;
        int cols = 5;
        double val = 5.5;
        double step = 0.1;

        var matrix = MamJv.fromRange(rows, cols, val, step);
        var shape = matrix.shape();

        Assertions.assertEquals(rows, shape[0]);
        Assertions.assertEquals(cols, shape[1]);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j, val += step) {
                Assertions.assertEquals(roundToOneDecimal(val), roundToOneDecimal(matrix.get(i, j)));
            }
        }
    }

    @Test
    void upperTriangular() {

        int rows = 5;
        int cols = 5;
        double val = 2.0;

        var matrix = MamJv.upperTriangular(rows, val);
        var shape = matrix.shape();

        Assertions.assertEquals(rows, shape[0]);
        Assertions.assertEquals(cols, shape[1]);

        for (int i = 0; i < rows; ++i) {
            for (int j = i; j < cols; ++j) {
                Assertions.assertEquals(val, matrix.get(i, j));
            }

            for (int j = 0; j < i; ++j) {
                Assertions.assertEquals(0, matrix.get(i, j));
            }
        }
    }

    @Test
    void lowerTriangular() {

        int rows = 5;
        int cols = 5;
        double val = 2.0;

        var matrix = MamJv.lowerTriangular(rows, val);
        var shape = matrix.shape();

        Assertions.assertEquals(rows, shape[0]);
        Assertions.assertEquals(cols, shape[1]);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < i+1; ++j) {
                Assertions.assertEquals(val, matrix.get(i, j));
            }

            for (int j = i+1; j < cols; ++j) {
                Assertions.assertEquals(0, matrix.get(i, j));
            }
        }
    }

    @Test
    void fromFunction() {

        int rows = 5;
        int cols = 5;

        var matrix = MamJv.fromFunction(rows, cols, () -> Math.E);
        var shape = matrix.shape();

        Assertions.assertEquals(rows, shape[0]);
        Assertions.assertEquals(cols, shape[1]);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                Assertions.assertEquals(Math.E, matrix.get(i, j));
            }
        }
    }

    @Test
    void fromFunction_withIndices() {

        int rows = 5;
        int cols = 5;

        var matrix = MamJv.fromFunction(rows, cols, (a, b) -> a * b);
        var shape = matrix.shape();

        Assertions.assertEquals(rows, shape[0]);
        Assertions.assertEquals(cols, shape[1]);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                Assertions.assertEquals(i*j, matrix.get(i, j));
            }
        }
    }

    private double roundToOneDecimal(double value) {
        return Math.round(value * 10) / 10.0;
    }
}