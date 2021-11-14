package com.lukastack.projectmatrix.benchmark;

import com.lukastack.projectmatrix.api.manipulations.MamJv;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.column.GroupColumnMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.column.GroupColumnOperation;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.element.GroupElementMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.element.GroupElementOperation;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.row.GroupRowMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.group.row.GroupRowOperation;
import com.lukastack.projectmatrix.parameters.poolproviders.singleton.SingletonThreadPoolProvider;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode({Mode.SingleShotTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1, jvmArgs = {"-Xms4G", "-Xmx4G"})
@Warmup(iterations = 2)
@Measurement(iterations = 20)
public class GroupBenchmark {

    @Param({"10", "100", "500", "1000", "2000"})
    private int size;

//    @Param({"0", "1", "2"})
    private int groupSize;

    private Matrix leftMatrix;
    private Matrix rightMatrix;
    private Matrix resultMatrix;

    private SingletonThreadPoolProvider poolProvider;

    private GroupColumnOperation columnOperation;
    private GroupColumnMatrixProduct columnMatrixProduct;
    private GroupRowOperation rowOperation;
    private GroupRowMatrixProduct rowMatrixProduct;
    private GroupElementOperation elementOperation;
    private GroupElementMatrixProduct elementMatrixProduct;

    @Setup
    public void setup() {

        leftMatrix = MamJv.uniformDistribution(size, size);
        rightMatrix = MamJv.uniformDistribution(size, size);
        resultMatrix = new MatJv(size, size);

        poolProvider = new SingletonThreadPoolProvider(16, 16, 10000L, TimeUnit.MILLISECONDS);

        int currentGroupSize = 16;
//        if (groupSize == 1) {
//            currentGroupSize = Runtime.getRuntime().availableProcessors();
//        }
//        else if (groupSize == 2) {
//            currentGroupSize = Math.max(1, size/8);
//        }

        columnOperation = new GroupColumnOperation(currentGroupSize);
        columnMatrixProduct = new GroupColumnMatrixProduct(currentGroupSize);

        rowOperation = new GroupRowOperation(currentGroupSize);
        rowMatrixProduct = new GroupRowMatrixProduct(currentGroupSize);

        elementOperation = new GroupElementOperation(currentGroupSize);
        elementMatrixProduct = new GroupElementMatrixProduct(currentGroupSize);
    }

//    @Benchmark
    public void rowPower() throws ExecutionException, InterruptedException {

        rowOperation.operate(leftMatrix, rightMatrix, resultMatrix, poolProvider.provideThreadPool(), (a, b) -> Math.pow(a, b));
        poolProvider.waitForCompletion();
        poolProvider.close();
    }

    @Benchmark
    public void rowPowerScalar() throws ExecutionException, InterruptedException {

        rowOperation.operate(leftMatrix, 86, resultMatrix, poolProvider.provideThreadPool(), (a, b) -> Math.pow(a, b));
        poolProvider.waitForCompletion();
        poolProvider.close();
    }

//    @Benchmark
    public void colPower() throws ExecutionException, InterruptedException {

        columnOperation.operate(leftMatrix, rightMatrix, resultMatrix, poolProvider.provideThreadPool(), (a, b) -> Math.pow(a, b));
        poolProvider.waitForCompletion();
        poolProvider.close();
    }

    @Benchmark
    public void colPowerScalar() throws ExecutionException, InterruptedException {

        columnOperation.operate(leftMatrix, 86, resultMatrix, poolProvider.provideThreadPool(), (a, b) -> Math.pow(a, b));
        poolProvider.waitForCompletion();
        poolProvider.close();
    }

//    @Benchmark
    public void rowMatrixProduct() throws ExecutionException, InterruptedException {

        rowMatrixProduct.operate(leftMatrix, rightMatrix, resultMatrix, poolProvider.provideThreadPool());

        poolProvider.waitForCompletion();
        poolProvider.close();
    }

//    @Benchmark
    public void colMatrixProduct() throws ExecutionException, InterruptedException {

        columnMatrixProduct.operate(leftMatrix, rightMatrix, resultMatrix, poolProvider.provideThreadPool());
        poolProvider.waitForCompletion();
        poolProvider.close();
    }

//    @Benchmark
    public void elementPower() throws ExecutionException, InterruptedException {

        elementOperation.operate(leftMatrix, rightMatrix, resultMatrix, poolProvider.provideThreadPool(), (a, b) -> Math.pow(a, b));
        poolProvider.waitForCompletion();
        poolProvider.close();
    }

    @Benchmark
    public void elementPowerScalar() throws ExecutionException, InterruptedException {

        elementOperation.operate(leftMatrix, 86, resultMatrix, poolProvider.provideThreadPool(), (a, b) -> Math.pow(a, b));
        poolProvider.waitForCompletion();
        poolProvider.close();
    }

//    @Benchmark
    public void elementMatrixProduct() throws ExecutionException, InterruptedException {

        elementMatrixProduct.operate(leftMatrix, rightMatrix, resultMatrix, poolProvider.provideThreadPool());

        poolProvider.waitForCompletion();
        poolProvider.close();
    }
}
