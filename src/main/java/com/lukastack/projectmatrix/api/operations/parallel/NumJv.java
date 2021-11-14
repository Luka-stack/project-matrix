package com.lukastack.projectmatrix.api.operations.parallel;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.row.GroupRowMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.row.GroupRowOperation;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultOperation;
import com.lukastack.projectmatrix.parameters.poolproviders.singleton.SingletonThreadPoolProvider;
import com.lukastack.projectmatrix.core.equations.NthRoot;

import java.util.concurrent.TimeUnit;

public class NumJv extends AbstractNumJv {

    private final GroupRowOperation rowOperation;
    private final GroupRowMatrixProduct rowMatrixProduct;
    private final SerialDefaultOperation serialOperation;
    private final SerialDefaultMatrixProduct serialMatrixProduct;

    public NumJv() {

        super(
                MatJv.class,
                new SingletonThreadPoolProvider(1, Runtime.getRuntime().availableProcessors(), 500L, TimeUnit.MILLISECONDS)
        );

        this.rowOperation = new GroupRowOperation();
        this.rowMatrixProduct = new GroupRowMatrixProduct();

        this.serialOperation = new SerialDefaultOperation();
        this.serialMatrixProduct = new SerialDefaultMatrixProduct();
    }

    @Override
    public Matrix add(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        var matrixShape = leftMatrix.shape();

        if (matrixShape[0] * matrixShape[1] >= 250000) {
            return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a + b);
        }

        return serialElementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a + b);
    }

    @Override
    public Matrix add(final Matrix matrix, double scalar) {

        var matrixShape = matrix.shape();

        if (matrixShape[0] * matrixShape[1] >= 250000) {
            return elementWiseOperation(matrix, scalar, (a, b) -> a + b);
        }

        return serialElementWiseOperation(matrix, scalar, (a, b) -> a + b);
    }

    @Override
    public Matrix sub(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        var matrixShape = leftMatrix.shape();

        if (matrixShape[0] * matrixShape[1] >= 250000) {
            return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a - b);
        }

        return serialElementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a - b);
    }

    @Override
    public Matrix sub(final Matrix matrix, double scalar) {

        var matrixShape = matrix.shape();

        if (matrixShape[0] * matrixShape[1] >= 250000) {
            return elementWiseOperation(matrix, scalar, (a, b) -> a - b);
        }

        return serialElementWiseOperation(matrix, scalar, (a, b) -> a - b);
    }

    @Override
    public Matrix mul(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        var matrixShape = leftMatrix.shape();

        if (matrixShape[0] * matrixShape[1] >= 250000) {
            return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a * b);
        }

        return serialElementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a * b);
    }

    @Override
    public Matrix mul(final Matrix matrix, double scalar) {

        var matrixShape = matrix.shape();

        if (matrixShape[0] * matrixShape[1] >= 250000) {
            return elementWiseOperation(matrix, scalar, (a, b) -> a * b);
        }

        return serialElementWiseOperation(matrix, scalar, (a, b) -> a * b);
    }

    @Override
    public Matrix div(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        var matrixShape = leftMatrix.shape();

        if (matrixShape[0] * matrixShape[1] >= 250000) {
            return elementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a / b);
        }

        return serialElementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a / b);
    }

    @Override
    public Matrix div(final Matrix matrix, double scalar) {

        var matrixShape = matrix.shape();

        if (matrixShape[0] * matrixShape[1] >= 250000) {
            return elementWiseOperation(matrix, scalar, (a, b) -> a / b);
        }

        return serialElementWiseOperation(matrix, scalar, (a, b) -> a / b);
    }

    @Override
    public Matrix pow(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        var matrixShape = leftMatrix.shape();

        if (matrixShape[0] * matrixShape[1] >= 250000) {
            return elementWiseOperation(leftMatrix, rightMatrix, Math::pow);
        }

        return serialElementWiseOperation(leftMatrix, rightMatrix, Math::pow);
    }

    @Override
    public Matrix pow(final Matrix matrix, double scalar) {

        var matrixShape = matrix.shape();

        if (matrixShape[0] * matrixShape[1] >= 250000) {
            return elementWiseOperation(matrix, scalar, Math::pow);
        }

        return serialElementWiseOperation(matrix, scalar, Math::pow);
    }

    @Override
    public Matrix root(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        var matrixShape = leftMatrix.shape();

        if (matrixShape[0] * matrixShape[1] >= 250000) {
            return elementWiseOperation(leftMatrix, rightMatrix, NthRoot::nthRoot);
        }

        return serialElementWiseOperation(leftMatrix, rightMatrix, NthRoot::nthRoot);
    }

    @Override
    public Matrix root(final Matrix matrix, double scalar) {

        var matrixShape = matrix.shape();

        if (matrixShape[0] * matrixShape[1] >= 250000) {
            return elementWiseOperation(matrix, scalar, NthRoot::nthRoot);
        }

        return serialElementWiseOperation(matrix, scalar, NthRoot::nthRoot);
    }

    @Override
    public Matrix matMul(Matrix leftMatrix, Matrix rightMatrix) {

        this.assertMatrixProductDimension(leftMatrix, rightMatrix);
        var result = createMatrix(leftMatrix.shape()[0], rightMatrix.shape()[1]);

        var matrixShape = leftMatrix.shape();
        if (matrixShape[0] * matrixShape[1] >= 80000) {

            threadPoolExecutor = poolProvider.provideThreadPool();
            this.rowMatrixProduct.operate(leftMatrix, rightMatrix, result, threadPoolExecutor);

            waitForResult();
            closeThreadPool();
        }
        else {
            this.serialMatrixProduct.operate(leftMatrix, rightMatrix, result);
        }

        return result;
    }

    private Matrix elementWiseOperation(final Matrix leftMatrix, final Matrix rightMatrix,
                                        final GenericEquation equation) {

        var result = createMatrix(leftMatrix.shape()[0], leftMatrix.shape()[1]);
        threadPoolExecutor = poolProvider.provideThreadPool();

        this.rowOperation.operate(leftMatrix, rightMatrix, result, threadPoolExecutor, equation);

        waitForResult();
        closeThreadPool();

        return result;
    }

    private Matrix elementWiseOperation(final Matrix matrix, double scalar,
                                        final GenericEquation equation) {

        var result = createMatrix(matrix.shape()[0], matrix.shape()[1]);
        threadPoolExecutor = poolProvider.provideThreadPool();

        this.rowOperation.operate(matrix, scalar, result, threadPoolExecutor, equation);

        waitForResult();
        closeThreadPool();

        return result;
    }

    private Matrix serialElementWiseOperation(final Matrix leftMatrix, final Matrix rightMatrix,
                                              final GenericEquation equation) {

        var result = createMatrix(leftMatrix.shape()[0], leftMatrix.shape()[1]);

        this.serialOperation.operate(leftMatrix, rightMatrix, result, equation);

        return result;
    }

    private Matrix serialElementWiseOperation(final Matrix matrix, double scalar,
                                              final GenericEquation equation) {

        var result = createMatrix(matrix.shape()[0], matrix.shape()[1]);

        this.serialOperation.operate(matrix, scalar, result, equation);

        return result;
    }
}
