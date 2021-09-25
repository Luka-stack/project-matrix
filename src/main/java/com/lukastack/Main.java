package com.lukastack;

import com.lukastack.projectmatrix.core.operations.GenericEquation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String... args) {

//        new LinkedBlockingQueue<>().getClass()
    }

    static double count(double a, double b, GenericEquation genericEquation) {
        return genericEquation.operate(a, b);
    }
}
