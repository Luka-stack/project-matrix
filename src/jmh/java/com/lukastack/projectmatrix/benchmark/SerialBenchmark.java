package com.lukastack.projectmatrix.benchmark;

import com.lukastack.projectmatrix.api.manipulations.MamJv;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialDefaultOperation;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

//@State(Scope.Benchmark)
//@BenchmarkMode({Mode.SingleShotTime})
//@OutputTimeUnit(TimeUnit.MILLISECONDS)
//@Fork(value = 1)
//@Warmup(iterations = 2)
//@Measurement(iterations = 10)
public class SerialBenchmark {

    @Param({"10", "100", "200", "300", "500", "1000", "2000"})
    private int size;

    private Matrix leftMatrix;
    private Matrix rightMatrix;
    private Matrix resultMatrix;

    private SerialDefaultOperation operations;
    private SerialDefaultMatrixProduct matrixProduct;

//    @Setup
    public void setup() {

        leftMatrix = MamJv.uniformDistribution(size, size);
        rightMatrix = MamJv.uniformDistribution(size, size);
        resultMatrix = new MatJv(size, size);

        operations = new SerialDefaultOperation();
        matrixProduct = new SerialDefaultMatrixProduct();
    }

    //@Benchmark
    public void power() {

        operations.operate(leftMatrix, rightMatrix, resultMatrix, (a, b) -> Math.pow(a, b));
    }

//    @Benchmark
    public void powerScalar() {

        operations.operate(leftMatrix, 65, resultMatrix, (a, b) -> Math.pow(a, b));
    }

//    @Benchmark
    public void matrixProduct() {

        matrixProduct.operate(leftMatrix, rightMatrix, resultMatrix);
    }
}
