package com.lukastack.projectmatrix.core.operations.definitions.parallel;

import com.lukastack.projectmatrix.core.matrices.Matrix;

import java.util.concurrent.ThreadPoolExecutor;

public interface Product {

    Matrix matMul(Matrix matLeft, Matrix matRight, ThreadPoolExecutor taskPool);
}
