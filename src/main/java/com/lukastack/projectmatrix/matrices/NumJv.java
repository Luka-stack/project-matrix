package com.lukastack.projectmatrix.matrices;

import com.lukastack.projectmatrix.utils.UniformDistribution;

public class NumJv {

    private NumJv() { }

    public static MatJv uniformMatrix(int rows, int cols) {

        MatJv result = new MatJv(rows, cols);

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                result.set(i, j, UniformDistribution.getRandom());
            }
        }

        return result;
    }

//    public static MatJv<Double> zeros(int rows, int cols) {
//
//        MatJv<Double> result = new MatJv<>(rows, cols);
//
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; ++j) {
//                result.set(i, j, 0.0);
//            }
//        }
//
//        return result;
//    }
//
//    public static <T extends Number> MatJv<T> zeros(int rows, int cols, Class<T> of)
//            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//
//        MatJv<T> result = new MatJv<>(rows, cols);
//        T zero = of.getConstructor(String.class).newInstance("0");
//
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; ++j) {
//                result.set(i, j, zero);
//            }
//        }
//
//        return result;
//    }
}
