package com.lukastack.projectmatrix.benchmark;

import com.lukastack.projectmatrix.api.manipulations.MamJv;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.column.AxisColumnMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.column.AxisColumnOperation;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.row.AxisRowMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.row.AxisRowOperation;
import com.lukastack.projectmatrix.parameters.poolproviders.singleton.SingletonThreadPoolProvider;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

//@State(Scope.Benchmark)
//@BenchmarkMode({Mode.SingleShotTime})
//@OutputTimeUnit(TimeUnit.MILLISECONDS)
//@Fork(value = 1, jvmArgs = {"-Xms4G", "-Xmx4G"})
//@Warmup(iterations = 2)
//@Measurement(iterations = 20)
public class AxisBenchmark {

//    @Param({"2000", "5000", "8000"})
    private int size;

//    @Param({"32", "64", "100"})
    private int corePoolSize;

    private Matrix leftMatrix;
    private Matrix rightMatrix;
    private Matrix resultMatrix;

    private SingletonThreadPoolProvider poolProvider;

    private AxisColumnOperation columnOperation;
    private AxisColumnMatrixProduct columnMatrixProduct;
    private AxisRowOperation rowOperation;
    private AxisRowMatrixProduct rowMatrixProduct;

//    @Setup
    public void setup() {

        leftMatrix = MamJv.uniformDistribution(size, size);
        rightMatrix = MamJv.uniformDistribution(size, size);
        resultMatrix = new MatJv(size, size);

        poolProvider = new SingletonThreadPoolProvider(corePoolSize);

        columnOperation = new AxisColumnOperation();
        columnMatrixProduct = new AxisColumnMatrixProduct();

        rowOperation = new AxisRowOperation();
        rowMatrixProduct = new AxisRowMatrixProduct();
    }

//    @Benchmark
    public void rowPower() throws ExecutionException, InterruptedException {

        rowOperation.operate(leftMatrix, rightMatrix, resultMatrix, poolProvider.provideThreadPool(), (a, b) -> Math.pow(a, b));
        poolProvider.waitForCompletion();
        poolProvider.close();
    }

    //@Benchmark
    public void colPower() throws ExecutionException, InterruptedException {

        columnOperation.operate(leftMatrix, rightMatrix, resultMatrix, poolProvider.provideThreadPool(), (a, b) -> Math.pow(a, b));
        poolProvider.waitForCompletion();
        poolProvider.close();
    }

//    @Benchmark
    public void rowMatrixProduct() throws ExecutionException, InterruptedException {

        rowMatrixProduct.operate(leftMatrix, rightMatrix, resultMatrix, poolProvider.provideThreadPool());

        poolProvider.waitForCompletion();
        poolProvider.close();
    }

    //@Benchmark
    public void colMatrixProduct() throws ExecutionException, InterruptedException {

        columnMatrixProduct.operate(leftMatrix, rightMatrix, resultMatrix, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();
        poolProvider.close();
    }
}
