package com.lukastack.projectmatrix.api.operations.parallel;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixOperation;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.GroupMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.row.GroupRowMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.row.GroupRowOperation;
import com.lukastack.projectmatrix.errors.CreationalException;
import com.lukastack.projectmatrix.parameters.poolproviders.singleton.SingletonThreadPoolProvider;
import com.lukastack.projectmatrix.parameters.poolproviders.ThreadPoolProvider;
import com.lukastack.projectmatrix.core.equations.NthRoot;

public class GroupNumJv extends AbstractNumJv {

    private final GroupMatrixOperation operationImpl;
    private final GroupMatrixProduct matrixProductImpl;
    private final boolean sharedThreadPool;
    private final boolean waitForResult;

    public GroupNumJv() {

        super();

        operationImpl = new GroupRowOperation();
        matrixProductImpl = new GroupRowMatrixProduct();
        sharedThreadPool = false;
        waitForResult = true;
    }

    public GroupNumJv(final GroupMatrixOperation operationImpl,
                           final GroupMatrixProduct matrixProductImpl,
                           final ThreadPoolProvider poolProvider, boolean sharedThreadPool, boolean waitForResult,
                           final Class<? extends Matrix> clazz) {

        super(clazz, poolProvider);

        this.operationImpl = operationImpl;
        this.matrixProductImpl = matrixProductImpl;
        this.sharedThreadPool = sharedThreadPool;
        this.waitForResult = waitForResult;
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

        if (threadPoolExecutor == null) {
            threadPoolExecutor = poolProvider.provideThreadPool();
        }

        this.matrixProductImpl.operate(leftMatrix, rightMatrix, result, threadPoolExecutor);

        if (waitForResult) {
            waitForResult();
        }

        if (!sharedThreadPool) {
            closeThreadPool();
        }

        return result;
    }

    private Matrix elementWiseOperation(final Matrix leftMatrix, final Matrix rightMatrix,
                                        final GenericEquation equation) {

        var result = createMatrix(leftMatrix.shape()[0], leftMatrix.shape()[1]);

        if (threadPoolExecutor == null) {
            threadPoolExecutor = poolProvider.provideThreadPool();
        }

        this.operationImpl.operate(leftMatrix, rightMatrix, result, threadPoolExecutor, equation);

        if (waitForResult) {
            waitForResult();
        }

        if (!sharedThreadPool) {
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

        if (!sharedThreadPool) {
            closeThreadPool();
        }

        return result;
    }

    public static class Builder {

        private GroupMatrixOperation operationsImpl;
        private GroupMatrixProduct matrixProductImpl;
        private Class<? extends Matrix> clazz = MatJv.class;
        private ThreadPoolProvider poolProvider = new SingletonThreadPoolProvider();
        private boolean sharedThreadPool = true;
        private boolean waitForResult = true;

        /**
         * Sets operationsImpl to provided implementation,
         * that GroupNumJv class will use to do element wise operations
         *
         * @param impl An instance of class that implements {@link GroupMatrixOperation} interface
         * @return GroupNumJv.Builder
         */
        public Builder operationsImpl(final GroupMatrixOperation impl) {

            this.operationsImpl = impl;
            return this;
        }

        /**
         * Sets matrixProductImpl to provided implementation,
         * that GroupNumJv class will use to do matrix product
         *
         * @param impl An instance of class that implements {@link GroupMatrixProduct} interface
         * @return GroupNumJv.Builder
         */
        public Builder matrixProductImpl(final GroupMatrixProduct impl) {

            this.matrixProductImpl = impl;
            return this;
        }

        /**
         * Sets clazz to provided implementation,
         * that GroupNumJv class will use to create new instance of {@link Matrix} that operations will return
         *
         * @param clazz An class that extends {@link Matrix} interface
         * @return GroupNumJv.Builder
         */
        public Builder matrixImpl(final Class<? extends Matrix> clazz) {

            this.clazz = clazz;
            return this;
        }

        /**
         * Sets poolProvider to provided implementation,
         * that GroupNumJv class will use to manipulate (create and close) pools of tasks
         *
         * @param poolProvider An instance of class that extends {@link ThreadPoolProvider} class
         * @return GroupNumJv.Builder
         */
        public Builder threadPoolProvider(final ThreadPoolProvider poolProvider) {

            this.poolProvider = poolProvider;
            return this;
        }

        /**
         * Sets sharedThreadPool parameter that controls closing pools after performing an operation
         *
         * @param bool if true, allow user to close thread on their own
         *             (meaning, thread pool will be shared by operation until user close it on its own)
         * @return GroupNumJv.Builder
         */
        public Builder sharedThreadPool(boolean bool) {

            this.sharedThreadPool = bool;
            return this;
        }

        /**
         * Sets waitForResult parameter that force the class to wait until all tasks will be finished.
         * This parameter is related to sharedThreadPool, false it to true sets sharedThreadPool also to false
         *
         * @param bool if true, immediately after submitting tasks to pool function will return
         * @return GroupNumJv.Builder
         */
        public Builder waitForResult(boolean bool) {

            this.waitForResult = bool;
            return this;
        }

        public GroupNumJv build() {

            if (operationsImpl == null || matrixProductImpl == null) {
                throw new CreationalException(
                        String.format("Cannot create instance of %s because implementations of operations were not provided",
                                this.getClass().getName())
                );
            }

            if (!waitForResult) {
                sharedThreadPool = true;
            }

            return new GroupNumJv(operationsImpl, matrixProductImpl, poolProvider, sharedThreadPool, waitForResult, clazz);
        }
    }
}
