package com.lukastack.projectmatrix.core.matrices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LiMatJv extends Matrix {

    private final List<List<Double>> data;

    public LiMatJv(int rows, int cols) {

        this.data = new ArrayList<>(rows);

        for (int i = 0; i < rows; ++i) {
            data.add(new ArrayList<>(Collections.nCopies(cols, 0.0)));
        }
    }

    @Override
    public void set(int row, int col, double value) {
        data.get(row).set(col, value);
    }

    @Override
    public double get(int row, int col) {
        return data.get(row).get(col);
    }

    @Override
    public int[] shape() {

        return new int[] { data.size(), data.get(0).size() };
    }

    @Override
    public IMatrix reshape(int rows, int cols) {

        if (rows*cols != this.data.size() * this.data.get(0).size()) {
            throw new IllegalArgumentException(String.format("Cannot reshape MatJv of size %d into shape (%d,%d)",
                    this.data.get(0).size()*this.data.size(), rows, cols));
        }

        var result = new LiMatJv(rows, cols);
        int oldCols = this.data.get(0).size();

        int i = 0;
        for (int r = 0; r < rows; ++r) {

            for (int c = 0; c < cols; ++c) {
                result.set(r, c, data.get(i/oldCols).get(i%oldCols));
                ++i;
            }
        }

        return null;
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
