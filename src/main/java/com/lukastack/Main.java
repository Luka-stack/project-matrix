package com.lukastack;

import com.lukastack.projectmatrix.api.concrete.IndividualBundle;
import com.lukastack.projectmatrix.core.matrices.LiMatJv;
import com.lukastack.projectmatrix.utils.UniformDistribution;
import com.lukastack.projectmatrix.wrapper.NumJv;

public class Main {

    public static void main(String... args) {

        var matrixOne = NumJv.uniformMatrix(5, 2);
        var matrixTwo = NumJv.uniformMatrix(2, 5);

        var invBundle = new IndividualBundle();
        var result = invBundle.matMul(matrixOne, matrixTwo);

        System.out.println(result);

        var liMatrixOne = new LiMatJv(5, 2);
        var liMatrixTwo = new LiMatJv(2, 5);

        for (int i = 0; i < liMatrixOne.shape()[0]; ++i) {
            for (int j = 0; j < liMatrixOne.shape()[1]; ++j) {
                liMatrixOne.set(i, j, UniformDistribution.getRandom());
            }
        }

        for (int i = 0; i < liMatrixTwo.shape()[0]; ++i) {
            for (int j = 0; j < liMatrixTwo.shape()[1]; ++j) {
                liMatrixTwo.set(i, j, UniformDistribution.getRandom());
            }
        }

        var invBundleTwo = new IndividualBundle(LiMatJv.class);
        var resultTwo = invBundleTwo.matMul(liMatrixOne, liMatrixTwo);

        System.out.println(resultTwo);
    }
}
