package com.lukastack;

import com.lukastack.projectmatrix.api.manipulations.MamJv;
import com.lukastack.projectmatrix.core.matrices.LiMatJv;
import com.lukastack.projectmatrix.core.matrices.MatJv;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String... args) throws InterruptedException, ExecutionException {

//        var m1 = NumJv.uniformMatrix(10000, 10000);
//        var m2 = NumJv.uniformMatrix(10000, 10000);
//
//        var m3 = NumJv.uniformMatrix(10000, 10000);
//        var m4 = NumJv.uniformMatrix(10000, 10000);
//
//        var serialOps = new SerialDefaultOperation();
//
//        long startTime = System.nanoTime();
//        serialOps.operate(m1, m2, m3, Math::pow);
//        long endTime = System.nanoTime();
//
//        System.out.println(endTime - startTime);
//
//        var axisRowOps = new AxisRowOperation();
//        var provider = new SingletonThreadPoolProvider(16, 16, 500L, TimeUnit.MILLISECONDS);
//
//        startTime = System.nanoTime();
//        axisRowOps.operate(m1, m2, m4, provider.provideThreadPool(), Math::pow);
//        provider.waitForCompletion();
//        provider.close();
//        endTime = System.nanoTime();
//
//        System.out.println(endTime - startTime);

        var test = new MatJv(3, 3);
        test.set(0, 0, 0.0);
        test.set(0, 1, 1.0);
        test.set(0, 2, 1.0);
        test.set(1, 0, 2.0);
        test.set(1, 1, 3.0);
        test.set(1, 2, 3.0);
        test.set(2, 0, 2.0);
        test.set(2, 1, 3.0);
        test.set(2, 2, 3.0);

        System.out.println(MamJv.stringifyMatrix(test));
    }
}
