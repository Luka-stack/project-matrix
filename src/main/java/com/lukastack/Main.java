package com.lukastack;

import com.lukastack.projectmatrix.core.serial.GenericEquation;

public class Main {

    public static void main(String... args) {


        System.out.println("Addition :"+ count(5, 5, Double::sum));
        System.out.println("Multiply :"+ count(5, 5, (a, b) -> a * b));
    }

    static double count(double a, double b, GenericEquation genericEquation) {
        return genericEquation.operate(a, b);
    }
}
