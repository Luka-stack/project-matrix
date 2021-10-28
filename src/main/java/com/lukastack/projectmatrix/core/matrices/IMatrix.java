package com.lukastack.projectmatrix.core.matrices;

interface IMatrix {

    void set(int row, int col, double value);

    double get(int row, int col);

    int[] shape();

    Matrix reshape(int rows, int cols);

    Matrix copy();
}
