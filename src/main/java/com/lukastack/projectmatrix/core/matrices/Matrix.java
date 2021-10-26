package com.lukastack.projectmatrix.core.matrices;

public interface Matrix {

    void set(int row, int col, double value);

    double get(int row, int col);

    int[] shape();

    Matrix reshape(int rows, int cols);

    Matrix copy();
}
