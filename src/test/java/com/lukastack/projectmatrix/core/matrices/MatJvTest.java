package com.lukastack.projectmatrix.core.matrices;

import com.lukastack.projectmatrix.errors.DimensionException;
import com.lukastack.projectmatrix.errors.DimensionsIndexException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatJvTest {

    private final int rows = 5;
    private final int cols = 2;
    private MatJv matrix;

    @BeforeEach
    void setUp() {
        matrix = new MatJv(rows, cols);
    }

    @Test
    void shape_returnCorrectShape() {

        var shape = matrix.shape();

        Assertions.assertEquals(rows, shape[0]);
        Assertions.assertEquals(cols, shape[1]);
    }

    @Test
    void set_setsCorrectCell() {

        double newValue = 2.34;

        var preCell = matrix.get(3, 1);
        matrix.set(3, 1, newValue);

        Assertions.assertNotEquals(newValue, preCell);
        Assertions.assertEquals(newValue, matrix.get(3, 1));
    }

    @Test
    void set_indexOutOfBounds_ThrowDimensionsIndexException() {

        Assertions.assertThrows(DimensionsIndexException.class, () -> matrix.set(rows*2, cols*3, 10.0));
    }

    @Test
    void get_returnCorrectCell() {

        int lookUpRow = 3;
        int lookUpCol = 1;
        double lookUpValue = 34.2;

        matrix.set(lookUpRow, lookUpCol, lookUpValue);

        Assertions.assertEquals(lookUpValue, matrix.get(lookUpRow, lookUpCol));
    }

    @Test
    void get_indexOutOfBounds_ThrowDimensionsIndexException() {

        Assertions.assertThrows(DimensionsIndexException.class, () -> matrix.get(rows*2, cols));
    }

    @Test
    void reshape_returnReshapedMatrix() {

        matrix.set(0, 0, 1);
        matrix.set(0, 1, 2);
        matrix.set(1, 0, 3);
        matrix.set(1, 1, 4);
        matrix.set(2, 0, 5);
        matrix.set(2, 1, 6);
        matrix.set(3, 0, 7);
        matrix.set(3, 1, 8);
        matrix.set(4, 0, 9);
        matrix.set(4, 1, 10);

        var result = matrix.reshape(cols, rows);
        var resultShape = result.shape();

        Assertions.assertEquals(cols, resultShape[0]);
        Assertions.assertEquals(rows, resultShape[1]);

        Assertions.assertEquals(matrix.get(0, 0), result.get(0, 0));
        Assertions.assertEquals(matrix.get(0, 1), result.get(0, 1));
        Assertions.assertEquals(matrix.get(1, 0), result.get(0, 2));
        Assertions.assertEquals(matrix.get(1, 1), result.get(0, 3));
        Assertions.assertEquals(matrix.get(2, 0), result.get(0, 4));
        Assertions.assertEquals(matrix.get(2, 1), result.get(1, 0));
        Assertions.assertEquals(matrix.get(3, 0), result.get(1, 1));
        Assertions.assertEquals(matrix.get(3, 1), result.get(1, 2));
        Assertions.assertEquals(matrix.get(4, 0), result.get(1, 3));
        Assertions.assertEquals(matrix.get(4, 1), result.get(1, 4));
    }

    @Test
    void reshape_providedWrongRowsAndCells_throwDimensionException() {

        Assertions.assertThrows(DimensionException.class, () -> matrix.reshape(rows * 5, cols * 2));
    }
}