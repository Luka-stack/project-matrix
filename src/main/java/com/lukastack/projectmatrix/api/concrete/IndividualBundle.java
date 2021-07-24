package com.lukastack.projectmatrix.api.concrete;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.parallel.individual.IndividualMatrixProductImpl;

public class IndividualBundle extends OperationsBundle {

    public IndividualBundle() {
        super(new IndividualMatrixProductImpl<>(MatJv.class));
    }

    public IndividualBundle(Class<? extends Matrix> clazz) {
        super(new IndividualMatrixProductImpl<>(clazz));
    }

    @Override
    public Matrix matMul(Matrix matOne, Matrix matTwo) {

        if (matOne.shape()[1] != matTwo.shape()[0]) {
            throw new IllegalArgumentException(
                    String.format("Input operand has a mismatch in its dimension (n?,k),(k,m?)->(n?,m?) " +
                    "(size %d is different from %d)",
                            matOne.shape()[0], matTwo.shape()[1]));
        }

        return this.matrixProductImpl.matMul(matOne, matTwo);
    }

}
