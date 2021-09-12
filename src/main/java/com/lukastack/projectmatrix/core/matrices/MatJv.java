package com.lukastack.projectmatrix.core.matrices;

public class MatJv extends Matrix {

    private final double[][] data;

    public MatJv(int rows, int cols) {
        this.data = new double[rows][cols];
    }

    @Override
    public void set(int row, int col, double value) {
        this.data[row][col] = value;
    }

    @Override
    public double get(int row, int col) {
        return this.data[row][col];
    }

    @Override
    public int[] shape() {
        return new int[] { this.data.length, this.data[0].length };
    }

    @Override
    public IMatrix reshape(int rows, int cols) {

        if (rows*cols != this.data.length * this.data[0].length) {
            throw new IllegalArgumentException(
                    String.format("Cannot reshape MatJv of size %d into shape (%d,%d)",
                            this.data[0].length*this.data.length, rows, cols));
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
