package com.lukastack;

import com.lukastack.projectmatrix.core.equations.GenericEquation;
import com.lukastack.projectmatrix.core.matrices.LiMatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.wrapper.NumJv;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {

    public static void main(String... args) {

        int a[] = new int[2];
        a[0] = 1;
        a[1] = 2;

        int b[] = new int[2];
        b[0] = 1;
        b[1] = 2;

        System.out.println(a == b);

    }
}
