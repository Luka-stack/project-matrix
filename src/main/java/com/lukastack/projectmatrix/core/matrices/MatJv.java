package com.lukastack.projectmatrix.core.matrices;

import com.lukastack.projectmatrix.errors.DimensionException;
import com.lukastack.projectmatrix.errors.DimensionsIndexException;

import java.util.Arrays;

public class MatJv implements Matrix {

    private final double[][] data;

    public MatJv(int rows, int cols) {
        this.data = new double[rows][cols];
    }

    public MatJv(int rows, int cols, double initialValue) {
        this.data = new double[rows][cols];
        for (double[] row: data)
            Arrays.fill(row, initialValue);
    }

    @Override
    public void set(int row, int col, double value) {

        try {
            this.data[row][col] = value;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new DimensionsIndexException(
                    String.format("Index [%d, %d] is out of bounds for size [%d, %d]",
                            row, col, shape()[0], shape()[1])
            );
        }
    }

    @Override
    public double get(int row, int col) {

        try {
            return this.data[row][col];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new DimensionsIndexException(
                    String.format("Index [%d, %d] is out of bounds for shape (%d, %d)",
                            row, col, shape()[0], shape()[1])
            );
        }

    }

    @Override
    public int[] shape() {
        return new int[] { this.data.length, this.data[0].length };
    }

    @Override
    public Matrix reshape(int rows, int cols) {

        if (rows*cols != this.data.length * this.data[0].length) {
            throw new DimensionException(
                    String.format("Cannot reshape Matrix of size %d into shape (%d, %d)",
                            this.data[0].length * this.data.length, rows, cols));
        }

        var result = new MatJv(rows, cols);
        int oldCols = this.data[0].length;

        int i = 0;
        for (int r = 0; r < rows; ++r) {

            for (int c = 0; c < cols; ++c) {
                result.set(r, c, get(i/oldCols, i%oldCols));
                ++i;
            }
        }

        return result;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder("MatJv([");

        for (double[] datum : data) {
            builder.append('[');
            String prefix = "";

            for (double value : datum) {
                builder.append(prefix);
                prefix = ",";
                builder.append(value);
            }
            builder.append("],\n");
        }
        builder.setLength(builder.length() - 3);
        builder.append("])");

        return builder.toString();
    }
}
