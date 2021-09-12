package com.lukastack.projectmatrix.wrapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumJvTest {

    @Test
    void uniformMatrix_returnsProperShapedMatrix() {

        var matrix = NumJv.uniformMatrix(5, 2);

        int rows = matrix.shape()[0];
        int cols = matrix.shape()[1];

        Assertions.assertEquals(5, rows);
        Assertions.assertEquals(2, cols);
    }
}