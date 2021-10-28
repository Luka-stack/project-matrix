package com.lukastack.projectmatrix.core.matrices;

import com.lukastack.projectmatrix.errors.CreationalException;
import com.lukastack.projectmatrix.errors.ImplementationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void matrixImplementationWithoutProperConstructor_ThrowImplementationException() {

        Assertions.assertThrows(ImplementationException.class, () -> new BadMatrixImplementation());
    }

    @Test
    void buildMatrix_providedClassWithoutProperConstructor_ThrowCreationalException() {

        Assertions.assertThrows(CreationalException.class, () -> Matrix.buildMatrix(BadMatrixImplementation.class, 2, 2));
    }

    static class BadMatrixImplementation extends Matrix {

        @Override
        public void set(int row, int col, double value) {

        }

        @Override
        public double get(int row, int col) {
            return 0;
        }

        @Override
        public int[] shape() {
            return new int[0];
        }

        @Override
        public Matrix reshape(int rows, int cols) {
            return null;
        }

        @Override
        public Matrix copy() {
            return null;
        }
    }
}