package com.lukastack;

import com.lukastack.projectmatrix.api.manipulations.MamJv;
import com.lukastack.projectmatrix.core.matrices.LiMatJv;
import com.lukastack.projectmatrix.core.matrices.MatJv;

import java.util.Arrays;
import java.util.concurrent.*;

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

        var pool = new ThreadPoolExecutor(-5, 5, 500L, TimeUnit.SECONDS,  new LinkedBlockingQueue<>());
    }
}
