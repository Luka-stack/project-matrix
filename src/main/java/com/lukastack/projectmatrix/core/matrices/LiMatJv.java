package com.lukastack.projectmatrix.core.matrices;

import com.lukastack.projectmatrix.errors.DimensionException;
import com.lukastack.projectmatrix.errors.DimensionsIndexException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LiMatJv implements Matrix {

    private final List<List<Double>> data;

    public LiMatJv(int rows, int cols) {

        this.data = new ArrayList<>(rows);

        for (int i = 0; i < rows; ++i) {
            data.add(new ArrayList<>(Collections.nCopies(cols, 0.0)));
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
    public String toString() {

        StringBuilder builder = new StringBuilder("LiMatJv([");

        for (List<Double> a : data) {
            builder.append('[');
            String prefix = "";

            for (Double aDouble : a) {
                builder.append(prefix);
                prefix = ",";
                builder.append(aDouble);
            }
            builder.append("],\n");
        }
        builder.setLength(builder.length() - 3);
        builder.append("])");

        return builder.toString();
    }
}
