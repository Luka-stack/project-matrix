package com.lukastack.projectmatrix.api.operations;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.CreateMatrix;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultOperation;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixOperation;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixProduct;
import com.lukastack.projectmatrix.errors.DimensionException;
import com.lukastack.projectmatrix.utils.NthRoot;

public class SerialNumJv implements CreateMatrix {

    private final SerialMatrixOperation operationImpl;
    private final SerialMatrixProduct matrixProductImpl;
    private final Class<? extends Matrix> clazz;

    public SerialNumJv() {

        operationImpl = new SerialDefaultOperation();
        matrixProductImpl = new SerialDefaultMatrixProduct();
        clazz = MatJv.class;
    }

    public SerialNumJv(final SerialMatrixOperation operationImpl, final SerialMatrixProduct matrixProductImpl,
                       final Class<? extends Matrix> clazz) {

        this.operationImpl = operationImpl;
        this.matrixProductImpl = matrixProductImpl;
        this.clazz = clazz;
    }

    public Matrix add(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a + b);
    }

    public Matrix add(final Matrix matrix, double scalar) {

        return elementWiseOperation(matrix, scalar, (a, b) -> a + b);
    }

    public Matrix sub(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a - b);
    }

    public Matrix sub(final Matrix matrix, double scalar) {

        return elementWiseOperation(matrix, scalar, (a, b) -> a - b);
    }

    public Matrix mul(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a * b);
    }

    public Matrix mul(final Matrix matrix, double scalar) {

        return elementWiseOperation(matrix, scalar, (a, b) -> a * b);
    }

    public Matrix div(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a / b);
    }

    public Matrix div(final Matrix matrix, double scalar) {

        return elementWiseOperation(matrix, scalar, (a, b) -> a / b);
    }

    public Matrix pow(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> Math.pow(a, b));
    }

    public Matrix pow(final Matrix matrix, double scalar) {

        return elementWiseOperation(matrix, scalar, (a, b) -> Math.pow(a, b));
    }

    public Matrix root(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> NthRoot.nthRoot(a, b));
    }

    public Matrix root(final Matrix matrix, double scalar) {

        return elementWiseOperation(matrix, scalar, (a, b) -> NthRoot.nthRoot(a, b));
    }

    public Matrix matMul(Matrix leftMatrix, Matrix rightMatrix) {

        this.assertMatrixProductDimension(leftMatrix, rightMatrix);

        var result = createMatrix(clazz, leftMatrix.shape()[0], rightMatrix.shape()[1]);

        this.matrixProductImpl.operate(leftMatrix, rightMatrix, result);

        return result;
    }

    private Matrix elementWiseOperation(final Matrix leftMatrix, final Matrix rightMatrix,
                                        final GenericEquation equation) {

        var result = createMatrix(clazz, leftMatrix.shape()[0], leftMatrix.shape()[1]);

        this.operationImpl.operate(leftMatrix, rightMatrix, result, equation);

        return result;
    }

    private Matrix elementWiseOperation(final Matrix leftMatrix, double scalar,
                                        final GenericEquation equation) {

        var result = createMatrix(clazz, leftMatrix.shape()[0], leftMatrix.shape()[1]);

        this.operationImpl.operate(leftMatrix, scalar, result, equation);

        return result;
    }

    private void assertElementWiseDimension(final Matrix leftMatrix, final Matrix rightMatrix) {

        if (leftMatrix.shape()[0] != rightMatrix.shape()[0] || leftMatrix.shape()[1] != rightMatrix.shape()[1]) {
            throw new DimensionException(String.format("Matrices dimensions are not equal, (%d, %d) != (%d, %d)",
                    leftMatrix.shape()[0], leftMatrix.shape()[1], rightMatrix.shape()[0], rightMatrix.shape()[1]));
        }
    }

    private void assertMatrixProductDimension(Matrix leftMatrix, Matrix rightMatrix) {

        if (leftMatrix.shape()[1] != rightMatrix.shape()[0]) {
            throw new DimensionException(
                    String.format("Left side Matrix's column size and right side Matrix's row size must be equal %d != %d",
                            leftMatrix.shape()[1], rightMatrix.shape()[0]));
        }
    }

    public static class Builder {

        private SerialMatrixOperation operationsImpl;
        private SerialMatrixProduct matrixProductImpl;
        private Class<? extends Matrix> clazz = MatJv.class;

        public Builder operationsImpl(final SerialMatrixOperation impl) {

            this.operationsImpl = impl;
            return this;
        }

        public Builder matrixProductImpl(final SerialMatrixProduct impl) {

            this.matrixProductImpl = impl;
            return this;
        }

        public Builder matrixImpl(final Class<? extends Matrix> clazz) {

            this.clazz = clazz;
            return this;
        }

        public SerialNumJv build() {

            return new SerialNumJv(operationsImpl, matrixProductImpl, clazz);
        }
    }
}
