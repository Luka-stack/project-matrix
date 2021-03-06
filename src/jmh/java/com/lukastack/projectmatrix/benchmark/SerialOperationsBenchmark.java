package com.lukastack.projectmatrix.benchmark;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.row.AxisRowOperation;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.row.AxisRowMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultOperation;
import com.lukastack.projectmatrix.parameters.poolproviders.singleton.SingletonThreadPoolProvider;
import com.lukastack.projectmatrix.wrapper.NumJv;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1)
@Warmup(iterations = 1)
@Measurement(iterations = 3)
public class SerialOperationsBenchmark {

    @Param({"2000"})
    private int N;

    private int B = 10000;

    private Matrix leftMatrix;
    private Matrix rightMatrix;
    private Matrix resultMatrix;

    private Matrix leftMatrixOp;
    private Matrix rightMatrixOp;
    private Matrix resultMatrixOp;

    private AxisRowMatrixProduct axisRowProduct;
    private AxisRowOperation axisRowOperations;
    private SerialDefaultOperation serialOperations;
    private SingletonThreadPoolProvider poolExecutor;

    @Setup
    public void setup() {

        leftMatrix = NumJv.uniformMatrix(N, N);
        rightMatrix = NumJv.uniformMatrix(N, N);
        resultMatrix = new MatJv(N, N);

        serialOperations = new SerialDefaultOperation();

        axisRowProduct = new AxisRowMatrixProduct();
        axisRowOperations = new AxisRowOperation();
        poolExecutor = new SingletonThreadPoolProvider(16, 16, 500L, TimeUnit.MILLISECONDS);

        leftMatrixOp = NumJv.uniformMatrix(B, B);
        rightMatrixOp = NumJv.uniformMatrix(B, B);
        resultMatrixOp = new MatJv(B, B);
    }

    @Benchmark
    public void axisRowProduct() throws ExecutionException, InterruptedException {

        axisRowProduct.operate(leftMatrix, rightMatrix, resultMatrix, poolExecutor.provideThreadPool());
        poolExecutor.waitForCompletion();
        poolExecutor.close();
    }

    @Benchmark
    public void axisRowPower() throws ExecutionException, InterruptedException {

        axisRowOperations.operate(leftMatrixOp, rightMatrixOp, resultMatrixOp, poolExecutor.provideThreadPool(), Math::pow);
        poolExecutor.waitForCompletion();
        poolExecutor.close();
    }

    @Benchmark
    public void serialPower() {

        serialOperations.operate(leftMatrixOp, rightMatrixOp, resultMatrixOp, Math::pow);
    }
}
