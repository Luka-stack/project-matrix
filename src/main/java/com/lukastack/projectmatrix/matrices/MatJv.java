package com.lukastack.projectmatrix.matrices;

public class MatJv<E extends Number> {

    private Object[][] data;

//    private E[] dim;
//    private E[] rows;
//    private E[] cols;

    public MatJv(int rows, int cols) {

        this.data = new Object[rows][cols];
//        this.data (E[][]) Array.newInstance(of, rows, cols);
    }

    public void set(int row, int col, E value) {
        this.data[row][col] = value;
    }

    @SuppressWarnings("unchecked")
    public E get(int row, int col) {
        return (E) this.data[row][col];
    }

    public int[] shape() {
        return new int[] { this.data.length, this.data[0].length };
    }

    public MatJv<E> reshape(int rows, int cols) {

        if (rows*cols != this.data.length * this.data[0].length) {
            throw new IllegalArgumentException(
                    String.format("Cannot reshape MatJv of size %d into shape (%d,%d)",
                            this.data[0].length*this.data.length, rows, cols));
        }

        var result = new MatJv<E>(rows, cols);
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
        for (Object[] datum : data) {

            builder.append('[');
            String prefix = "";
            for (int col = 0; col < data[0].length; ++col) {
                builder.append(prefix);
                prefix = ",";
                builder.append(datum[col]);
            }
            builder.append("],\n");
        }
        builder.setLength(builder.length() - 3);
        builder.append("])");

        return builder.toString();
    }
}
