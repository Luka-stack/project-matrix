package com.lukastack.projectmatrix.core.matrices;

public interface IMatrix {

    void set(int row, int col, double value);

    double get(int row, int col);

    int[] shape();

    IMatrix reshape(int rows, int cols);
}
