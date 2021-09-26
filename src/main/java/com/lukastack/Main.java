package com.lukastack;

import com.lukastack.projectmatrix.core.equations.GenericEquation;

public class Main {

    public static void main(String... args) {

//        new LinkedBlockingQueue<>().getClass()
    }

    static double count(double a, double b, GenericEquation genericEquation) {
        return genericEquation.operate(a, b);
    }
}
