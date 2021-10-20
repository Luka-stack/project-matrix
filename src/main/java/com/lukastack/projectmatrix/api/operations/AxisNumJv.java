package com.lukastack.projectmatrix.api.operations;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.AxisMatrixOperation;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.AxisMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.row.AxisRowMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.row.AxisRowOperation;
import com.lukastack.projectmatrix.parameters.threads.SingletonThreadPoolProvider;
import com.lukastack.projectmatrix.parameters.threads.ThreadPoolProvider;
import com.lukastack.projectmatrix.utils.NthRoot;

public class AxisNumJv extends AbstractNumJv {

    private final AxisMatrixOperation operationImpl;
    private final AxisMatrixProduct matrixProductImpl;

    public AxisNumJv() {

        super();

        operationImpl = new AxisRowOperation();
        matrixProductImpl = new AxisRowMatrixProduct();
    }

    public AxisNumJv(final AxisMatrixOperation operationImpl, final AxisMatrixProduct matrixProductImpl,
                     final ThreadPoolProvider poolProvider, boolean sharedThreadPool, boolean waitForResult,
                     final Class<? extends Matrix> clazz) {

        super(clazz, poolProvider, sharedThreadPool, waitForResult);

        this.operationImpl = operationImpl;
        this.matrixProductImpl = matrixProductImpl;
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

        var result = createMatrix(leftMatrix.shape()[0], rightMatrix.shape()[1]);

        if (threadPoolExecutor != null) {
            threadPoolExecutor = poolProvider.provideThreadPool();
        }

        this.matrixProductImpl.operate(leftMatrix, rightMatrix, result, threadPoolExecutor);

        if (waitForResult) {
            waitForResult();
        }

        if (closeOnFinish) {
            closeThreadPool();
        }

        return result;
    }

    private Matrix elementWiseOperation(final Matrix leftMatrix, final Matrix rightMatrix,
                                        final GenericEquation equation) {

        var result = createMatrix(leftMatrix.shape()[0], leftMatrix.shape()[1]);

        if (threadPoolExecutor != null) {
            threadPoolExecutor = poolProvider.provideThreadPool();
        }

        this.operationImpl.operate(leftMatrix, rightMatrix, result, threadPoolExecutor, equation);

        if (waitForResult) {
            waitForResult();
        }

        if (closeOnFinish) {
            closeThreadPool();
        }

        return result;
    }

    private Matrix elementWiseOperation(final Matrix leftMatrix, double scalar,
                                        final GenericEquation equation) {

        var result = createMatrix(leftMatrix.shape()[0], leftMatrix.shape()[1]);

        if (threadPoolExecutor == null) {
            threadPoolExecutor = poolProvider.provideThreadPool();
        }

        this.operationImpl.operate(leftMatrix, scalar, result, threadPoolExecutor, equation);

        if (waitForResult) {
            waitForResult();
        }

        if (closeOnFinish) {
            closeThreadPool();
        }

        return result;
    }

    public static class Builder {

        private AxisMatrixOperation operationsImpl;
        private AxisMatrixProduct matrixProductImpl;
        private Class<? extends Matrix> clazz = MatJv.class;
        private ThreadPoolProvider poolProvider = new SingletonThreadPoolProvider();
        private boolean closeOnFinish = true;
        private boolean waitForResult = true;

        public Builder operationsImpl(final AxisMatrixOperation impl) {

            this.operationsImpl = impl;
            return this;
        }

        public Builder matrixProductImpl(final AxisMatrixProduct impl) {

            this.matrixProductImpl = impl;
            return this;
        }

        public Builder matrixImpl(final Class<? extends Matrix> clazz) {

            this.clazz = clazz;
            return this;
        }

        public Builder threadPoolProvider(final ThreadPoolProvider poolProvider) {

            this.poolProvider = poolProvider;
            return this;
        }

        public Builder sharedThreadPool() {

            this.closeOnFinish = true;
            return this;
        }

        public Builder waitForResult() {

            this.waitForResult = true;
            return this;
        }

        public AxisNumJv build() {

            return new AxisNumJv(operationsImpl, matrixProductImpl, poolProvider, closeOnFinish, waitForResult, clazz);
        }
    }
}
