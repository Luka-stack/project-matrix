package com.lukastack;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.LiMatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.row.AxisRowOperations;
import com.lukastack.projectmatrix.wrapper.NumJv;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {

    public static void main(String... args) {

        int index = 3;
        int maxRowId = 2 + 1;
        int maxColId = 4 + 1;

        int column = index % maxColId;
        int row = (index / maxColId) % maxRowId;

        System.out.println(row +" "+ column);

        System.out.println(1 % 5);
    }
}
