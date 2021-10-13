package com.lukastack;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.LiMatJv;
import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.api.serial.SerialMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.row.AxisRowOperations;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialMatrixProductOperation;
import com.lukastack.projectmatrix.core.operations.implementations.serial.SerialProductOperation;
import com.lukastack.projectmatrix.wrapper.NumJv;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {

    public static void main(String... args) {

        var m1 = NumJv.uniformMatrix(1000, 1000);
        var m2 = NumJv.uniformMatrix(1000, 1000);

        SerialMatrixProduct operation = new SerialMatrixProduct(MatJv.class);
        operation.matMul(m1, m2);
    }
}
