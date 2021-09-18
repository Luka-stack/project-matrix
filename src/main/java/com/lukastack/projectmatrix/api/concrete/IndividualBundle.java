package com.lukastack.projectmatrix.api.concrete;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.parallel.individual.IndividualMatrixProductImpl;
import com.lukastack.projectmatrix.parameters.threads.AbstractThreadPoolProvider;
import com.lukastack.projectmatrix.parameters.threads.SingletonThreadPoolProvider;

public class IndividualBundle extends OperationsBundle {

    /**
     * Default Constructor
     * Create Bundle with Individual implementation, and fixed SingletonThreadPool
     */
    public IndividualBundle() {
        super(new IndividualMatrixProductImpl<>(MatJv.class), new SingletonThreadPoolProvider());
    }

    public IndividualBundle(Class<? extends Matrix> clazz, AbstractThreadPoolProvider poolProvider) {
        super(new IndividualMatrixProductImpl<>(clazz), poolProvider);
    }

    @Override
    public Matrix matMul(Matrix matOne, Matrix matTwo) {

        if (matOne.shape()[1] != matTwo.shape()[0]) {
            throw new IllegalArgumentException(
                    String.format("Input operand has a mismatch in its dimension (n?,k),(k,m?)->(n?,m?) " +
                    "(size %d is different from %d)",
                            matOne.shape()[0], matTwo.shape()[1]));
        }

        var result = this.matrixProductImpl.matMul(matOne, matTwo, this.threadPoolProvider.provideThreadPool());

        threadPoolProvider.close();

        return result;
    }

}
