package com.lukastack;

import com.lukastack.projectmatrix.core.serial.GenericEquation;
import com.lukastack.projectmatrix.utils.NthRoot;

public class Main {

    public static void main(String... args) {
    }

    static double count(double a, double b, GenericEquation genericEquation) {
        return genericEquation.operate(a, b);
    }
}
