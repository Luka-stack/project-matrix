package com.lukastack;

import com.lukastack.projectmatrix.matrices.NumJv;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Main {

    public static void main(String... args)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        var doubleMatrix = NumJv.uniformMatrix(4, 6);
        System.out.println(doubleMatrix);
        System.out.println(Arrays.toString(doubleMatrix.shape()));

//        var reshapedMatrix = doubleMatrix.reshape(6, 4);
//        System.out.println(reshapedMatrix);
//        System.out.println(reshapedMatrix.shape());
    }
}
