package com.lukastack.projectmatrix.api.operations.serial;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultOperation;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixOperation;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixProduct;
import com.lukastack.projectmatrix.core.equations.NthRoot;

public class SerialNumJv extends AbstractSerialNumJv {

    private final SerialMatrixOperation operationImpl;
    private final SerialMatrixProduct matrixProductImpl;

    public SerialNumJv() {

        super();

        operationImpl = new SerialDefaultOperation();
        matrixProductImpl = new SerialDefaultMatrixProduct();
    }

    public SerialNumJv(final SerialMatrixOperation operationImpl, final SerialMatrixProduct matrixProductImpl,
                       final Class<? extends Matrix> clazz) {

        super(clazz);

        this.operationImpl = operationImpl;
        this.matrixProductImpl = matrixProductImpl;
    }

    @Override
    public Matrix add(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a + b);
    }

    @Override
    public Matrix add(final Matrix matrix, double scalar) {

        return elementWiseOperation(matrix, scalar, (a, b) -> a + b);
    }

    @Override
    public Matrix sub(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a - b);
    }

    @Override
    public Matrix sub(final Matrix matrix, double scalar) {

        return elementWiseOperation(matrix, scalar, (a, b) -> a - b);
    }

    @Override
    public Matrix mul(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a * b);
    }

    @Override
    public Matrix mul(final Matrix matrix, double scalar) {

        return elementWiseOperation(matrix, scalar, (a, b) -> a * b);
    }

    @Override
    public Matrix div(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a / b);
    }

    @Override
    public Matrix div(final Matrix matrix, double scalar) {

        return elementWiseOperation(matrix, scalar, (a, b) -> a / b);
    }

    @Override
    public Matrix pow(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> Math.pow(a, b));
    }

    @Override
    public Matrix pow(final Matrix matrix, double scalar) {

        return elementWiseOperation(matrix, scalar, (a, b) -> Math.pow(a, b));
    }

    @Override
    public Matrix root(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> NthRoot.nthRoot(a, b));
    }

    @Override
    public Matrix root(final Matrix matrix, double scalar) {

        return elementWiseOperation(matrix, scalar, (a, b) -> NthRoot.nthRoot(a, b));
    }

    @Override
    public Matrix matMul(Matrix leftMatrix, Matrix rightMatrix) {

        this.assertMatrixProductDimension(leftMatrix, rightMatrix);

        var result = createMatrix(leftMatrix.shape()[0], rightMatrix.shape()[1]);

        this.matrixProductImpl.operate(leftMatrix, rightMatrix, result);

        return result;
    }

    private Matrix elementWiseOperation(final Matrix leftMatrix, final Matrix rightMatrix,
                                        final GenericEquation equation) {

        var result = createMatrix(leftMatrix.shape()[0], leftMatrix.shape()[1]);

        this.operationImpl.operate(leftMatrix, rightMatrix, result, equation);

        return result;
    }

    private Matrix elementWiseOperation(final Matrix leftMatrix, double scalar,
                                        final GenericEquation equation) {

        var result = createMatrix(leftMatrix.shape()[0], leftMatrix.shape()[1]);

        this.operationImpl.operate(leftMatrix, scalar, result, equation);

        return result;
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
