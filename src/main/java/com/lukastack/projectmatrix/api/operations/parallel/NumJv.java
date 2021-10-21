package com.lukastack.projectmatrix.api.operations.parallel;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.column.GroupColumnMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.column.GroupColumnOperation;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.row.GroupRowMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.row.GroupRowOperation;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultOperation;
import com.lukastack.projectmatrix.parameters.poolproviders.singleton.SingletonThreadPoolProvider;
import com.lukastack.projectmatrix.core.equations.NthRoot;

import java.util.concurrent.TimeUnit;

public class NumJv extends AbstractNumJv {

    private final GroupRowOperation rowOperation;
    private final GroupColumnOperation columnOperation;
    private final GroupRowMatrixProduct rowMatrixProduct;
    private final GroupColumnMatrixProduct columnMatrixProduct;
    private final SerialDefaultOperation serialOperation;
    private final SerialDefaultMatrixProduct serialMatrixProduct;

    public NumJv() {

        super(
                MatJv.class,
                new SingletonThreadPoolProvider(1, Runtime.getRuntime().availableProcessors(), 500L, TimeUnit.MILLISECONDS),
                true,
                true
                );

        this.rowOperation = new GroupRowOperation();
        this.columnOperation = new GroupColumnOperation();

        this.rowMatrixProduct = new GroupRowMatrixProduct();
        this.columnMatrixProduct = new GroupColumnMatrixProduct();

        this.serialOperation = new SerialDefaultOperation();
        this.serialMatrixProduct = new SerialDefaultMatrixProduct();
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
        threadPoolExecutor = poolProvider.provideThreadPool();

        if (result.shape()[0] < result.shape()[1] * 2) {
            this.rowMatrixProduct.operate(leftMatrix, rightMatrix, result, threadPoolExecutor);
        }
        else {
            this.columnMatrixProduct.operate(leftMatrix, rightMatrix, result, threadPoolExecutor);
        }

        waitForResult();
        closeThreadPool();

        return result;
    }

    public Matrix serialAdd(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return serialElementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a + b);
    }

    public Matrix serialAdd(final Matrix matrix, double scalar) {

        return serialElementWiseOperation(matrix, scalar, (a, b) -> a + b);
    }

    public Matrix serialSub(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return serialElementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a - b);
    }

    public Matrix serialSub(final Matrix matrix, double scalar) {

        return serialElementWiseOperation(matrix, scalar, (a, b) -> a - b);
    }

    public Matrix serialMul(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return serialElementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a * b);
    }

    public Matrix serialMul(final Matrix matrix, double scalar) {

        return serialElementWiseOperation(matrix, scalar, (a, b) -> a * b);
    }

    public Matrix serialDiv(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return serialElementWiseOperation(leftMatrix, rightMatrix, (a, b) -> a / b);
    }

    public Matrix serialDiv(final Matrix matrix, double scalar) {

        return serialElementWiseOperation(matrix, scalar, (a, b) -> a / b);
    }

    public Matrix serialPow(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return serialElementWiseOperation(leftMatrix, rightMatrix, (a, b) -> Math.pow(a, b));
    }

    public Matrix serialPow(final Matrix matrix, double scalar) {

        return serialElementWiseOperation(matrix, scalar, (a, b) -> Math.pow(a, b));
    }

    public Matrix serialRoot(final Matrix leftMatrix, final Matrix rightMatrix) {

        this.assertElementWiseDimension(leftMatrix, rightMatrix);

        return serialElementWiseOperation(leftMatrix, rightMatrix, (a, b) -> NthRoot.nthRoot(a, b));
    }

    public Matrix serialRoot(final Matrix matrix, double scalar) {

        return serialElementWiseOperation(matrix, scalar, (a, b) -> NthRoot.nthRoot(a, b));
    }

    public Matrix serialMatMul(Matrix leftMatrix, Matrix rightMatrix) {

        this.assertMatrixProductDimension(leftMatrix, rightMatrix);
        var result = createMatrix(leftMatrix.shape()[0], rightMatrix.shape()[1]);

        this.serialMatrixProduct.operate(leftMatrix, rightMatrix, result);

        return result;
    }

    private Matrix elementWiseOperation(final Matrix leftMatrix, final Matrix rightMatrix,
                                        final GenericEquation equation) {

        var result = createMatrix(leftMatrix.shape()[0], leftMatrix.shape()[1]);
        threadPoolExecutor = poolProvider.provideThreadPool();

        if (result.shape()[0] < result.shape()[1] * 2) {
            this.rowOperation.operate(leftMatrix, rightMatrix, result, threadPoolExecutor, equation);
        }
        else {
            this.columnOperation.operate(leftMatrix, rightMatrix, result, threadPoolExecutor, equation);
        }

        waitForResult();
        closeThreadPool();

        return result;
    }

    private Matrix elementWiseOperation(final Matrix matrix, double scalar,
                                        final GenericEquation equation) {

        var result = createMatrix(matrix.shape()[0], matrix.shape()[1]);
        threadPoolExecutor = poolProvider.provideThreadPool();

        if (result.shape()[0] < result.shape()[1] * 2) {
            this.rowOperation.operate(matrix, scalar, result, threadPoolExecutor, equation);
        }
        else {
            this.columnOperation.operate(matrix, scalar, result, threadPoolExecutor, equation);
        }

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
