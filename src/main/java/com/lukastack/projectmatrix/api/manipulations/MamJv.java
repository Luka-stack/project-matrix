package com.lukastack.projectmatrix.api.manipulations;

import com.lukastack.projectmatrix.core.equations.ProduceValue;
import com.lukastack.projectmatrix.core.equations.ProduceValueFromIndexes;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.errors.DimensionException;
import com.lukastack.projectmatrix.utils.NormalDistribution;
import com.lukastack.projectmatrix.utils.UniformDistribution;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MamJv {

    public static String stringifyMatrix(final Matrix matrix) {

        StringBuilder builder = new StringBuilder("Matrix([");
        var shape = matrix.shape();
        String prefix;

        for (int row = 0; row < shape[0]; ++row) {
            builder.append('[');
            prefix = "";

            for (int col = 0; col < shape[1]; ++col) {
                builder.append(prefix);
                prefix = ", ";
                builder.append(matrix.get(row, col));
            }
            builder.append("], \n\t\t");
        }
        builder.setLength(builder.length() - 5);
        builder.append("])");

        return builder.toString();
    }

    public static Matrix uniformDistribution(int rows, int cols) {

        return fromFunction(rows, cols, UniformDistribution::getRandom);
    }

    public static Matrix uniformDistribution(int rows, int cols, Class<? extends Matrix> clazz) {

        return fromFunction(rows, cols, UniformDistribution::getRandom, clazz);
    }

    public static Matrix normalDistribution(int rows, int cols) {

        return fromFunction(rows, cols, NormalDistribution::getRandom);
    }

    public static Matrix normalDistribution(int rows, int cols, Class<? extends Matrix> clazz) {

        return fromFunction(rows, cols, NormalDistribution::getRandom, clazz);
    }

    public static Matrix zeros(int rows, int cols) {

        return fromFunction(rows, cols, () -> 0);
    }

    public static Matrix zeros(int rows, int cols, Class<? extends Matrix> clazz) {

        return fromFunction(rows, cols, () -> 0, clazz);
    }

    public static Matrix ones(int rows, int cols) {

        return fromFunction(rows, cols, () -> 1);
    }

    public static Matrix ones(int rows, int cols, Class<? extends Matrix> clazz) {

        return fromFunction(rows, cols, () -> 1, clazz);
    }

    public static Matrix withValue(int rows, int cols, double value) {

        return fromFunction(rows, cols, () -> value);
    }

    public static Matrix withValue(int rows, int cols, double value, Class<? extends Matrix> clazz) {

        return fromFunction(rows, cols, () -> value, clazz);
    }

    public static Matrix fromRange(int rows, int cols) {

        var result = Matrix.buildMatrix(MatJv.class, rows, cols);

        Stream.iterate(0, n -> n < rows*cols, n -> ++n)
                .parallel()
                .forEach(x -> result.set(
                        (x / cols) % rows,
                        x % cols,
                        x
                ));

        return result;
    }

    public static Matrix fromRange(int rows, int cols, Class<? extends Matrix> clazz) {

        var result = Matrix.buildMatrix(clazz, rows, cols);

        Stream.iterate(0, n -> n < rows*cols, n -> ++n)
                .parallel()
                .forEach(x -> result.set(
                        (x / cols) % rows,
                        x % cols,
                        x
                ));

        return result;
    }

    public static Matrix fromRange(int rows, int cols, double start) {

        var result = Matrix.buildMatrix(MatJv.class, rows, cols);

        Stream.iterate(0, n -> n < rows*cols, n -> ++n)
                .parallel()
                .forEach(x -> result.set(
                        (x / cols) % rows,
                        x % cols,
                        start + x
                ));

        return result;
    }

    public static Matrix fromRange(int rows, int cols, double start, Class<? extends Matrix> clazz) {

        var result = Matrix.buildMatrix(clazz, rows, cols);

        Stream.iterate(0, n -> n < rows*cols, n -> ++n)
                .parallel()
                .forEach(x -> result.set(
                        (x / cols) % rows,
                        x % cols,
                        start + x
                ));

        return result;
    }

    public static Matrix fromRange(int rows, int cols, double start, double step) {

        var result = Matrix.buildMatrix(MatJv.class, rows, cols);

        Stream.iterate(0, n -> n < rows*cols, n -> ++n)
                .parallel()
                .forEach(x -> result.set(
                        (x / cols) % rows,
                        x % cols,
                        start + step * x
                ));

        return result;
    }

    public static Matrix fromRange(int rows, int cols, double start, double step, Class<? extends Matrix> clazz) {

        var result = Matrix.buildMatrix(clazz, rows, cols);

        Stream.iterate(0, n -> n < rows*cols, n -> ++n)
                .parallel()
                .forEach(x -> result.set(
                        (x / cols) % rows,
                        x % cols,
                        start + step * x
                ));

        return result;
    }

    public static Matrix upperTriangular(int rows, int cols, double value) {

        if (rows != cols) {
            throw new DimensionException(
                    String.format("Upper Triangular Matrix has to be square but provided shape was (%d, %d)",
                            rows, cols)
            );
        }

        var result = Matrix.buildMatrix(MatJv.class, rows, cols);

        IntStream.iterate(0, r -> r < rows, r -> ++r).parallel().forEach(x -> {
            IntStream.iterate(x, c -> c < cols, c -> ++c).parallel().forEach(y -> result.set(x, y, value));
            IntStream.iterate(0, c -> c < x, c -> ++c).parallel().forEach(y -> result.set(x, y, 0));
        });

        return result;
    }

    public static Matrix upperTriangular(int rows, int cols, double value, Class<? extends Matrix> clazz) {

        if (rows != cols) {
            throw new DimensionException(
                    String.format("Upper Triangular Matrix has to be square but provided shape was (%d, %d)",
                            rows, cols)
            );
        }

        var result = Matrix.buildMatrix(clazz, rows, cols);

        IntStream.iterate(0, r -> r < rows, r -> ++r).parallel().forEach(x -> {
            IntStream.iterate(x, c -> c < cols, c -> ++c).parallel().forEach(y -> result.set(x, y, value));
            IntStream.iterate(0, c -> c < x, c -> ++c).parallel().forEach(y -> result.set(x, y, 0));
        });

        return result;
    }

    public static Matrix lowerTriangular(int rows, int cols, double value) {

        if (rows != cols) {
            throw new DimensionException(
                    String.format("Lower Triangular Matrix has to be square but provided shape was (%d, %d)",
                            rows, cols)
            );
        }

        var result = Matrix.buildMatrix(MatJv.class, rows, cols);

        IntStream.iterate(0, r -> r < rows, r -> ++r)
                .parallel()
                .forEach(x -> {
                    IntStream.iterate(0, c -> c < x+1, c -> ++c)
                            .parallel()
                            .forEach(y -> result.set(x, y, value));
                    IntStream.iterate(x+1, c -> c < cols, c -> ++c)
                            .parallel()
                            .forEach(y -> result.set(x, y, 0));
        });

        return result;
    }

    public static Matrix lowerTriangular(int rows, int cols, double value, Class<? extends Matrix> clazz) {

        if (rows != cols) {
            throw new DimensionException(
                    String.format("Lower Triangular Matrix has to be square but provided shape was (%d, %d)",
                            rows, cols)
            );
        }

        var result = Matrix.buildMatrix(clazz, rows, cols);

        IntStream.iterate(0, r -> r < rows, r -> ++r)
                .parallel()
                .forEach(x -> {
                    IntStream.iterate(0, c -> c < x+1, c -> ++c)
                            .parallel()
                            .forEach(y -> result.set(x, y, value));
                    IntStream.iterate(x+1, c -> c < cols, c -> ++c)
                            .parallel()
                            .forEach(y -> result.set(x, y, 0));
                });

        return result;
    }

    public static Matrix fromFunction(int rows, int cols, final ProduceValue produceValue) {

        var result = Matrix.buildMatrix(MatJv.class, rows, cols);

        Stream.iterate(1, n -> n <= rows*cols, n -> ++n)
                .parallel()
                .forEach(x -> result.set(
                        (x / cols) % rows,
                        x % cols,
                        produceValue.produce()
                ));

        return result;
    }

    public static Matrix fromFunction(int rows, int cols, final ProduceValue produceValue,
                                      Class<? extends Matrix> clazz) {

        var result = Matrix.buildMatrix(clazz, rows, cols);

        Stream.iterate(1, n -> n <= rows*cols, n -> ++n)
                .parallel()
                .forEach(x -> result.set(
                        (x / cols) % rows,
                        x % cols,
                        produceValue.produce()
                ));

        return result;
    }

    public static Matrix fromFunction(int rows, int cols, final ProduceValueFromIndexes produceValue) {

        var result = Matrix.buildMatrix(MatJv.class, rows, cols);

        Stream.iterate(1, n -> n <= rows*cols, n -> ++n)
                .parallel()
                .forEach(x -> result.set(
                        (x / cols) % rows,
                        x % cols,
                        produceValue.produce((x / cols) % rows, x % cols)
                ));

        return result;
    }

    public static Matrix fromFunction(int rows, int cols, final ProduceValueFromIndexes produceValue,
                                      Class<? extends Matrix> clazz) {

        var result = Matrix.buildMatrix(clazz, rows, cols);

        Stream.iterate(1, n -> n <= rows*cols, n -> ++n)
                .parallel()
                .forEach(x -> result.set(
                        (x / cols) % rows,
                        x % cols,
                        produceValue.produce((x / cols) % rows, x % cols)
                ));

        return result;
    }

    private MamJv() {

        throw new IllegalStateException("Utility class");
    }
}
