package com.lukastack.projectmatrix.core.matrices;

import com.lukastack.projectmatrix.errors.DimensionException;
import com.lukastack.projectmatrix.errors.DimensionsIndexException;

import java.util.*;

public class LiMatJv implements Matrix {

    private final List<List<Double>> data;

    public LiMatJv(List<List<Double>> data) {

        if (data.isEmpty()) {
            throw new DimensionException("Cannot create Matrix out of empty List");
        }

        int size = data.get(0).size();
        this.data = new ArrayList<>(data.size());

        for (List<Double> col : data) {

            if (col.size() != size) {
                throw new DimensionException("Matrix must be homogeneous");
            }

            this.data.add(new ArrayList<>(col));
        }
    }

    public LiMatJv(int rows, int cols) {

        if (rows == 0 || cols == 0) {
            throw new DimensionException("Cannot create Matrix with one of its dimensions equal to 0");
        }

        this.data = new ArrayList<>(rows);

        for (int i = 0; i < rows; ++i) {
            data.add(new ArrayList<>(Collections.nCopies(cols, 0.0)));
        }
    }

    public LiMatJv(int rows, int cols, double initialValue) {

        if (rows == 0 || cols == 0) {
            throw new DimensionException("Cannot create Matrix with one of its dimensions equal to 0");
        }

        this.data = new ArrayList<>(rows);

        for (int i = 0; i < rows; ++i) {
            data.add(new ArrayList<>(Collections.nCopies(cols, initialValue)));
        }
    }

    @Override
    public void set(int row, int col, double value) {

        try {
            data.get(row).set(col, value);
        }
        catch (IndexOutOfBoundsException e) {
            throw new DimensionsIndexException(
                    String.format("Index [%d, %d] is out of bounds for size [%d, %d]",
                            row, col, shape()[0], shape()[1])
            );
        }
    }

    @Override
    public double get(int row, int col) {

        try {
            return data.get(row).get(col);
        }
        catch (IndexOutOfBoundsException e) {
            throw new DimensionsIndexException(
                    String.format("Index [%d, %d] is out of bounds for shape (%d, %d)",
                            row, col, shape()[0], shape()[1])
            );
        }
    }

    @Override
    public int[] shape() {

        return new int[]{data.size(), data.get(0).size()};
    }

    @Override
    public Matrix reshape(int rows, int cols) {

        if (rows * cols != this.data.size() * this.data.get(0).size()) {
            throw new DimensionException(
                    String.format("Cannot reshape Matrix of size %d into shape (%d, %d)",
                            this.data.get(0).size() * this.data.size(), rows, cols));
        }

        var result = new LiMatJv(rows, cols);
        int oldCols = this.data.get(0).size();

        int i = 0;
        for (int r = 0; r < rows; ++r) {

            for (int c = 0; c < cols; ++c) {
                result.set(r, c, data.get(i / oldCols).get(i % oldCols));
                ++i;
            }
        }

        return result;
    }

    @Override
    public Matrix copy() {

        return new LiMatJv(this.data);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiMatJv liMatJv = (LiMatJv) o;
        return data.equals(liMatJv.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(data);
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder("LiMatJv([");

        for (List<Double> a : data) {
            builder.append('[');
            String prefix = "";

            for (Double aDouble : a) {
                builder.append(prefix);
                prefix = ", ";
                builder.append(aDouble);
            }
            builder.append("],\n\t\t ");
        }
        builder.setLength(builder.length() - 5);
        builder.append("])");

        return builder.toString();
    }
}
