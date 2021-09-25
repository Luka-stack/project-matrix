package com.lukastack.projectmatrix.api.concrete;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualMatrixProduct;
import com.lukastack.projectmatrix.parameters.threads.ThreadPoolProvider;
import com.lukastack.projectmatrix.parameters.threads.SingletonThreadPoolProvider;

import java.util.concurrent.ExecutionException;

public class IndividualBundle extends OperationsBundle {

    /**
     * Default Constructor
     * Create Bundle with Individual implementation, and fixed SingletonThreadPool
     */
    public IndividualBundle() {
        super(new IndividualMatrixProduct(MatJv.class), new SingletonThreadPoolProvider());
    }

    public IndividualBundle(Class<? extends Matrix> clazz, ThreadPoolProvider poolProvider) {
        super(new IndividualMatrixProduct(clazz), poolProvider);
    }

    @Override
    public Matrix matMul(Matrix matOne, Matrix matTwo) throws ExecutionException, InterruptedException {

        if (matOne.shape()[1] != matTwo.shape()[0]) {
            throw new IllegalArgumentException(
                    String.format("Input operand has a mismatch in its dimension (n?,k),(k,m?)->(n?,m?) " +
                    "(size %d is different from %d)",
                            matOne.shape()[0], matTwo.shape()[1]));
        }

        var result = this.matrixProductImpl.matMul(matOne, matTwo, this.threadPoolProvider.provideThreadPool());

        threadPoolProvider.waitForCompletion();
        threadPoolProvider.close();

        return result;
    }

}
