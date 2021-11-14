package com.lukastack.projectmatrix.benchmark;

import com.lukastack.projectmatrix.api.manipulations.MamJv;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualDefaultMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.individual.IndividualDefaultOperation;
import com.lukastack.projectmatrix.parameters.poolproviders.singleton.SingletonThreadPoolProvider;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

//@State(Scope.Benchmark)
//@BenchmarkMode({Mode.SingleShotTime})
//@OutputTimeUnit(TimeUnit.MILLISECONDS)
//@Fork(value = 1)
//@Warmup(iterations = 2)
//@Measurement(iterations = 20)
public class IndividualBenchmark {

//    @Param({"500"})
    private int size;

    //@Param({"0", "1", "2", "3"})
    private int corePoolSize;

    //@Param({"0", "10", "100", "500"})
    private int keepAlive;

    private Matrix leftMatrix;
    private Matrix rightMatrix;
    private Matrix resultMatrix;

    private SingletonThreadPoolProvider poolProvider;

    private IndividualDefaultOperation operations;
    private IndividualDefaultMatrixProduct matrixProduct;

    //@Setup
    public void setup() {

        leftMatrix = MamJv.uniformDistribution(size, size);
        rightMatrix = MamJv.uniformDistribution(size, size);
        resultMatrix = new MatJv(size, size);

        poolProvider = new SingletonThreadPoolProvider(1, (int) Math.pow(2, Math.log10(size)), keepAlive, TimeUnit.MILLISECONDS);

        operations = new IndividualDefaultOperation();
        matrixProduct = new IndividualDefaultMatrixProduct();
    }

    //@Benchmark
    public void power() throws ExecutionException, InterruptedException {

        operations.operate(leftMatrix, rightMatrix, resultMatrix, poolProvider.provideThreadPool(), (a, b) -> Math.pow(a, b));
        poolProvider.waitForCompletion();
        poolProvider.close();
    }

    //@Benchmark
    public void matrixProduct() throws ExecutionException, InterruptedException {

        matrixProduct.operate(leftMatrix, rightMatrix, resultMatrix, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();
        poolProvider.close();
    }
}
