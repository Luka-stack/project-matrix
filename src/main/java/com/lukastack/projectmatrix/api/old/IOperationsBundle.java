package com.lukastack.projectmatrix.api.old;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ExecutionException;

public interface IOperationsBundle {

    Matrix matMul(Matrix matOne, Matrix matTwo) throws ExecutionException, InterruptedException;
}
