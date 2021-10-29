package com.lukastack;

import com.lukastack.projectmatrix.api.manipulations.MamJv;
import com.lukastack.projectmatrix.core.matrices.LiMatJv;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualDefaultOperation;
import com.lukastack.projectmatrix.parameters.poolproviders.singleton.SingletonThreadPoolProvider;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.*;

public class Main {

    public static void main(String... args) throws InterruptedException, ExecutionException {

        var m1 = MamJv.uniformDistribution(10, 10);
        var m2 = MamJv.uniformDistribution(10, 10);
        var m3 = new MatJv(10, 10);

        var axisRowOps = new IndividualDefaultOperation();
        var provider = new SingletonThreadPoolProvider();

        axisRowOps.operate(m1, m2, m3, provider.provideThreadPool(), Math::pow);
        provider.waitForCompletion();
        provider.close();

        axisRowOps.operate(m1, m2, m3, provider.provideThreadPool(), Math::pow);
        provider.waitForCompletion();
        provider.close();

        long startTime = System.nanoTime();
        axisRowOps.operate(m1, m2, m3, provider.provideThreadPool(), Math::pow);
        provider.waitForCompletion();
        provider.close();
        long endTime = System.nanoTime();

        System.out.println(endTime - startTime);
    }
}
