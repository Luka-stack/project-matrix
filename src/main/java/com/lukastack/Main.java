package com.lukastack;

import com.lukastack.projectmatrix.api.concrete.IndividualBundle;
import com.lukastack.projectmatrix.wrapper.NumJv;

public class Main {

    public static void main(String... args) {

        var bundle = new IndividualBundle();

        var matrixOne = NumJv.uniformMatrix(5, 2);
        var matrixTwo = NumJv.uniformMatrix(2, 5);

        var result = bundle.matMul(matrixOne, matrixTwo);

        System.out.println(result);
    }
}
