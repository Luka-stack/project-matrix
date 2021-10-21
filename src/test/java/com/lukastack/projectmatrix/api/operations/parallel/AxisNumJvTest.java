package com.lukastack.projectmatrix.api.operations.parallel;

import com.lukastack.projectmatrix.core.matrices.MatJv;
import com.lukastack.projectmatrix.core.matrices.Matrix;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.column.AxisColumnMatrixProduct;
import com.lukastack.projectmatrix.core.operations.implementations.parallel.axis.row.AxisRowOperation;
import com.lukastack.projectmatrix.errors.CreationalException;
import com.lukastack.projectmatrix.errors.DimensionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

class AxisNumJvTest {

    private final DecimalFormat toOneDecimal = new DecimalFormat("0.0");
    private final DecimalFormat toThreeDecimal = new DecimalFormat("0.000");
    private Matrix leftMatrix;
    private Matrix rightMatrix;
    private double scalar;
    private AxisNumJv axisNumJv;

    @BeforeEach
    void setUp() {
        axisNumJv = new AxisNumJv();
    }

    @Test
    void add_Matrix_v_Matrix_CorrectEquation() {

        leftMatrix = new MatJv(3, 3);
        rightMatrix = new MatJv(3, 3);

        leftMatrix.set(0, 0, 5.0);
        leftMatrix.set(0, 1, 8.0);
        leftMatrix.set(0, 2, 1.0);
        leftMatrix.set(1, 0, 7.0);
        leftMatrix.set(1, 1, 1.0);
        leftMatrix.set(1, 2, 5.0);
        leftMatrix.set(2, 0, 3.0);
        leftMatrix.set(2, 1, 2.0);
        leftMatrix.set(2, 2, 0.0);

        rightMatrix.set(0, 0, 7.0);
        rightMatrix.set(0, 1, 5.0);
        rightMatrix.set(0, 2, 1.0);
        rightMatrix.set(1, 0, 2.0);
        rightMatrix.set(1, 1, 4.0);
        rightMatrix.set(1, 2, 1.0);
        rightMatrix.set(2, 0, 1.0);
        rightMatrix.set(2, 1, 1.0);
        rightMatrix.set(2, 2, 1.0);

        var result = axisNumJv.add(leftMatrix, rightMatrix);

        Assertions.assertEquals(12.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(13.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(2.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(9.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(5.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(6.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(4.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(3.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(1.0, Double.parseDouble(toOneDecimal.format(result.get(2, 2))));
    }

    @Test
    void add_Matrix_x_Matrix_RowImplementation_WrongDimensions_ThrowsDimensionException() {

        leftMatrix = new MatJv(2, 2);
        rightMatrix = new MatJv(5, 2);

        Assertions.assertThrows(DimensionException.class, () -> axisNumJv.add(leftMatrix, rightMatrix));
    }

    @Test
    void add_Matrix_v_Scalar_CorrectEquation() {
        leftMatrix = new MatJv(3, 3);
        scalar = 9;

        leftMatrix.set(0, 0, 3.0);
        leftMatrix.set(0, 1, 7.0);
        leftMatrix.set(0, 2, 2.0);
        leftMatrix.set(1, 0, 6.0);
        leftMatrix.set(1, 1, 2.0);
        leftMatrix.set(1, 2, 3.0);
        leftMatrix.set(2, 0, 1.0);
        leftMatrix.set(2, 1, 8.0);
        leftMatrix.set(2, 2, 3.0);

        var result = axisNumJv.add(leftMatrix, scalar);

        Assertions.assertEquals(12.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(16.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(11.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(15.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(11.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(12.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(10.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(17.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(12.0, Double.parseDouble(toOneDecimal.format(result.get(2, 2))));
    }

    @Test
    void sub_Matrix_v_Matrix_CorrectEquation() {

        leftMatrix = new MatJv(3, 3);
        rightMatrix = new MatJv(3, 3);

        leftMatrix.set(0, 0, 5.0);
        leftMatrix.set(0, 1, 8.0);
        leftMatrix.set(0, 2, 1.0);
        leftMatrix.set(1, 0, 7.0);
        leftMatrix.set(1, 1, 1.0);
        leftMatrix.set(1, 2, 5.0);
        leftMatrix.set(2, 0, 3.0);
        leftMatrix.set(2, 1, 2.0);
        leftMatrix.set(2, 2, 0.0);

        rightMatrix.set(0, 0, 7.0);
        rightMatrix.set(0, 1, 5.0);
        rightMatrix.set(0, 2, 1.0);
        rightMatrix.set(1, 0, 2.0);
        rightMatrix.set(1, 1, 4.0);
        rightMatrix.set(1, 2, 1.0);
        rightMatrix.set(2, 0, 1.0);
        rightMatrix.set(2, 1, 1.0);
        rightMatrix.set(2, 2, 1.0);

        var result = axisNumJv.sub(leftMatrix, rightMatrix);

        Assertions.assertEquals(-2.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(3.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(0.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(5.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(-3.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(4.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(2.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(1.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(-1.0, Double.parseDouble(toOneDecimal.format(result.get(2, 2))));
    }

    @Test
    void sub_Matrix_x_Matrix_RowImplementation_WrongDimensions_ThrowsDimensionException() {

        leftMatrix = new MatJv(2, 2);
        rightMatrix = new MatJv(5, 2);

        Assertions.assertThrows(DimensionException.class, () -> axisNumJv.sub(leftMatrix, rightMatrix));
    }

    @Test
    void sub_Matrix_v_Scalar_CorrectEquation() {

        leftMatrix = new MatJv(3, 3);
        scalar = 9;

        leftMatrix.set(0, 0, 3.0);
        leftMatrix.set(0, 1, 7.0);
        leftMatrix.set(0, 2, 2.0);
        leftMatrix.set(1, 0, 6.0);
        leftMatrix.set(1, 1, 2.0);
        leftMatrix.set(1, 2, 3.0);
        leftMatrix.set(2, 0, 1.0);
        leftMatrix.set(2, 1, 8.0);
        leftMatrix.set(2, 2, 3.0);

        var result = axisNumJv.sub(leftMatrix, scalar);

        Assertions.assertEquals(-6.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(-2.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(-7.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(-3.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(-7.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(-6.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(-8.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(-1.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(-6.0, Double.parseDouble(toOneDecimal.format(result.get(2, 2))));
    }

    @Test
    void mul_Matrix_v_Matrix_CorrectEquation() {

        leftMatrix = new MatJv(3, 3);
        rightMatrix = new MatJv(3, 3);

        leftMatrix.set(0, 0, 5.0);
        leftMatrix.set(0, 1, 8.0);
        leftMatrix.set(0, 2, 1.0);
        leftMatrix.set(1, 0, 7.0);
        leftMatrix.set(1, 1, 1.0);
        leftMatrix.set(1, 2, 5.0);
        leftMatrix.set(2, 0, 3.0);
        leftMatrix.set(2, 1, 2.0);
        leftMatrix.set(2, 2, 0.0);

        rightMatrix.set(0, 0, 7.0);
        rightMatrix.set(0, 1, 5.0);
        rightMatrix.set(0, 2, 1.0);
        rightMatrix.set(1, 0, 2.0);
        rightMatrix.set(1, 1, 4.0);
        rightMatrix.set(1, 2, 1.0);
        rightMatrix.set(2, 0, 1.0);
        rightMatrix.set(2, 1, 1.0);
        rightMatrix.set(2, 2, 1.0);

        var result = axisNumJv.mul(leftMatrix, rightMatrix);

        Assertions.assertEquals(35.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(40.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(1.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(14.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(4.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(5.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(3.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(2.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(0.0, Double.parseDouble(toOneDecimal.format(result.get(2, 2))));
    }

    @Test
    void mul_Matrix_x_Matrix_RowImplementation_WrongDimensions_ThrowsDimensionException() {

        leftMatrix = new MatJv(2, 2);
        rightMatrix = new MatJv(5, 2);

        Assertions.assertThrows(DimensionException.class, () -> axisNumJv.mul(leftMatrix, rightMatrix));
    }

    @Test
    void mul_Matrix_v_Scalar_CorrectEquation() {

        leftMatrix = new MatJv(3, 3);
        scalar = 9;

        leftMatrix.set(0, 0, 3.0);
        leftMatrix.set(0, 1, 7.0);
        leftMatrix.set(0, 2, 2.0);
        leftMatrix.set(1, 0, 6.0);
        leftMatrix.set(1, 1, 2.0);
        leftMatrix.set(1, 2, 3.0);
        leftMatrix.set(2, 0, 1.0);
        leftMatrix.set(2, 1, 8.0);
        leftMatrix.set(2, 2, 3.0);

        var result = axisNumJv.mul(leftMatrix, scalar);

        Assertions.assertEquals(27.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(63.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(18.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(54.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(18.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(27.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(9.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(72.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(27.0, Double.parseDouble(toOneDecimal.format(result.get(2, 2))));
    }

    @Test
    void div_Matrix_v_Matrix_CorrectEquation() {

        leftMatrix = new MatJv(3, 3);
        rightMatrix = new MatJv(3, 3);

        leftMatrix.set(0, 0, 5.0);
        leftMatrix.set(0, 1, 8.0);
        leftMatrix.set(0, 2, 1.0);
        leftMatrix.set(1, 0, 7.0);
        leftMatrix.set(1, 1, 1.0);
        leftMatrix.set(1, 2, 5.0);
        leftMatrix.set(2, 0, 3.0);
        leftMatrix.set(2, 1, 2.0);
        leftMatrix.set(2, 2, 0.0);

        rightMatrix.set(0, 0, 7.0);
        rightMatrix.set(0, 1, 5.0);
        rightMatrix.set(0, 2, 1.0);
        rightMatrix.set(1, 0, 2.0);
        rightMatrix.set(1, 1, 4.0);
        rightMatrix.set(1, 2, 1.0);
        rightMatrix.set(2, 0, 1.0);
        rightMatrix.set(2, 1, 1.0);
        rightMatrix.set(2, 2, 1.0);

        var result = axisNumJv.div(leftMatrix, rightMatrix);

        Assertions.assertEquals(0.714, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(1.600, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(1.000, Double.parseDouble(toThreeDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(3.500, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(0.250, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(5.000, Double.parseDouble(toThreeDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(3.000, Double.parseDouble(toThreeDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(2.000, Double.parseDouble(toThreeDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(0.000, Double.parseDouble(toThreeDecimal.format(result.get(2, 2))));
    }

    @Test
    void div_Matrix_x_Matrix_RowImplementation_WrongDimensions_ThrowsDimensionException() {

        leftMatrix = new MatJv(2, 2);
        rightMatrix = new MatJv(5, 2);

        Assertions.assertThrows(DimensionException.class, () -> axisNumJv.div(leftMatrix, rightMatrix));
    }

    @Test
    void div_Matrix_v_Scalar_CorrectEquation() {

        leftMatrix = new MatJv(3, 3);
        scalar = 9;

        leftMatrix.set(0, 0, 3.0);
        leftMatrix.set(0, 1, 7.0);
        leftMatrix.set(0, 2, 2.0);
        leftMatrix.set(1, 0, 6.0);
        leftMatrix.set(1, 1, 2.0);
        leftMatrix.set(1, 2, 3.0);
        leftMatrix.set(2, 0, 1.0);
        leftMatrix.set(2, 1, 8.0);
        leftMatrix.set(2, 2, 3.0);

        var result = axisNumJv.div(leftMatrix, scalar);

        Assertions.assertEquals(0.333, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(0.778, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(0.222, Double.parseDouble(toThreeDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(0.667, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(0.222, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(0.333, Double.parseDouble(toThreeDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(0.111, Double.parseDouble(toThreeDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(0.889, Double.parseDouble(toThreeDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(0.333, Double.parseDouble(toThreeDecimal.format(result.get(2, 2))));
    }

    @Test
    void pow_Matrix_v_Matrix_CorrectEquation() {

        leftMatrix = new MatJv(3, 3);
        rightMatrix = new MatJv(3, 3);

        leftMatrix.set(0, 0, 5.0);
        leftMatrix.set(0, 1, 8.0);
        leftMatrix.set(0, 2, 1.0);
        leftMatrix.set(1, 0, 7.0);
        leftMatrix.set(1, 1, 1.0);
        leftMatrix.set(1, 2, 5.0);
        leftMatrix.set(2, 0, 3.0);
        leftMatrix.set(2, 1, 2.0);
        leftMatrix.set(2, 2, 0.0);

        rightMatrix.set(0, 0, 7.0);
        rightMatrix.set(0, 1, 5.0);
        rightMatrix.set(0, 2, 1.0);
        rightMatrix.set(1, 0, 2.0);
        rightMatrix.set(1, 1, 4.0);
        rightMatrix.set(1, 2, 1.0);
        rightMatrix.set(2, 0, 1.0);
        rightMatrix.set(2, 1, 1.0);
        rightMatrix.set(2, 2, 1.0);

        var result = axisNumJv.pow(leftMatrix, rightMatrix);

        Assertions.assertEquals(78125.000, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(32768.000, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(1.000, Double.parseDouble(toThreeDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(49.000, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(1.000, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(5.000, Double.parseDouble(toThreeDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(3.000, Double.parseDouble(toThreeDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(2.000, Double.parseDouble(toThreeDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(0.000, Double.parseDouble(toThreeDecimal.format(result.get(2, 2))));
    }

    @Test
    void pow_Matrix_x_Matrix_RowImplementation_WrongDimensions_ThrowsDimensionException() {

        leftMatrix = new MatJv(2, 2);
        rightMatrix = new MatJv(5, 2);

        Assertions.assertThrows(DimensionException.class, () -> axisNumJv.pow(leftMatrix, rightMatrix));
    }

    @Test
    void pow_Matrix_v_Scalar_CorrectEquation() {

        leftMatrix = new MatJv(3, 3);
        scalar = 2;

        leftMatrix.set(0, 0, 3.0);
        leftMatrix.set(0, 1, 7.0);
        leftMatrix.set(0, 2, 2.0);
        leftMatrix.set(1, 0, 6.0);
        leftMatrix.set(1, 1, 2.0);
        leftMatrix.set(1, 2, 3.0);
        leftMatrix.set(2, 0, 1.0);
        leftMatrix.set(2, 1, 8.0);
        leftMatrix.set(2, 2, 3.0);

        var result = axisNumJv.pow(leftMatrix, scalar);

        Assertions.assertEquals(9.0, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(49.0, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(4.0, Double.parseDouble(toThreeDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(36.0, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(4.0, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(9.0, Double.parseDouble(toThreeDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(1.0, Double.parseDouble(toThreeDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(64.0, Double.parseDouble(toThreeDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(9.0, Double.parseDouble(toThreeDecimal.format(result.get(2, 2))));
    }

    @Test
    void root_Matrix_v_Matrix_CorrectEquation() {

        leftMatrix = new MatJv(2, 2);
        rightMatrix = new MatJv(2, 2);

        leftMatrix.set(0, 0, 4.0);
        leftMatrix.set(0, 1, 9.0);
        leftMatrix.set(1, 0, 27.0);
        leftMatrix.set(1, 1, 125.0);

        rightMatrix.set(0, 0, 2.0);
        rightMatrix.set(0, 1, 2.0);
        rightMatrix.set(1, 0, 3.0);
        rightMatrix.set(1, 1, 3.0);

        var result = axisNumJv.root(leftMatrix, rightMatrix);

        Assertions.assertEquals(2.000, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(3.000, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(3.00, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(5.00, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
    }

    @Test
    void root_Matrix_x_Matrix_RowImplementation_WrongDimensions_ThrowsDimensionException() {

        leftMatrix = new MatJv(2, 2);
        rightMatrix = new MatJv(5, 2);

        Assertions.assertThrows(DimensionException.class, () -> axisNumJv.root(leftMatrix, rightMatrix));
    }

    @Test
    void root_Matrix_v_Scalar_CorrectEquation() {

        leftMatrix = new MatJv(2, 2);
        scalar = 2;

        leftMatrix.set(0, 0, 49.0);
        leftMatrix.set(0, 1, 100.0);
        leftMatrix.set(1, 0, 81.0);
        leftMatrix.set(1, 1, 144.0);

        var result = axisNumJv.root(leftMatrix, scalar);

        Assertions.assertEquals(7.0, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(10.0, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(9.0, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(12.0, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
    }

    @Test
    void matMul_CorrectEquation() {

        leftMatrix = new MatJv(5, 2);
        rightMatrix = new MatJv(2, 5);

        leftMatrix.set(0, 0, 1.2);
        leftMatrix.set(0, 1, 2.2);
        leftMatrix.set(1, 0, 3.2);
        leftMatrix.set(1, 1, 4.2);
        leftMatrix.set(2, 0, 5.2);
        leftMatrix.set(2, 1, 6.2);
        leftMatrix.set(3, 0, 7.2);
        leftMatrix.set(3, 1, 8.2);
        leftMatrix.set(4, 0, 9.2);
        leftMatrix.set(4, 1, 10.2);

        rightMatrix.set(0, 0, 0.99);
        rightMatrix.set(0, 1, 0.88);
        rightMatrix.set(0, 2, 0.77);
        rightMatrix.set(0, 3, 0.66);
        rightMatrix.set(0, 4, 0.55);
        rightMatrix.set(1, 0, 0.44);
        rightMatrix.set(1, 1, 0.33);
        rightMatrix.set(1, 2, 0.22);
        rightMatrix.set(1, 3, 0.11);
        rightMatrix.set(1, 4, 0.01);

        var result = axisNumJv.matMul(leftMatrix, rightMatrix);

        Assertions.assertEquals(2.156, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(1.782, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(1.408, Double.parseDouble(toThreeDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(1.034, Double.parseDouble(toThreeDecimal.format(result.get(0, 3))));
        Assertions.assertEquals(0.682, Double.parseDouble(toThreeDecimal.format(result.get(0, 4))));

        Assertions.assertEquals(5.016, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(4.202, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(3.388, Double.parseDouble(toThreeDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(2.574, Double.parseDouble(toThreeDecimal.format(result.get(1, 3))));
        Assertions.assertEquals(1.802, Double.parseDouble(toThreeDecimal.format(result.get(1, 4))));

        Assertions.assertEquals(7.876, Double.parseDouble(toThreeDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(6.622, Double.parseDouble(toThreeDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(5.368, Double.parseDouble(toThreeDecimal.format(result.get(2, 2))));
        Assertions.assertEquals(4.114, Double.parseDouble(toThreeDecimal.format(result.get(2, 3))));
        Assertions.assertEquals(2.922, Double.parseDouble(toThreeDecimal.format(result.get(2, 4))));

        Assertions.assertEquals(10.736, Double.parseDouble(toThreeDecimal.format(result.get(3, 0))));
        Assertions.assertEquals(9.042, Double.parseDouble(toThreeDecimal.format(result.get(3, 1))));
        Assertions.assertEquals(7.348, Double.parseDouble(toThreeDecimal.format(result.get(3, 2))));
        Assertions.assertEquals(5.654, Double.parseDouble(toThreeDecimal.format(result.get(3, 3))));
        Assertions.assertEquals(4.042, Double.parseDouble(toThreeDecimal.format(result.get(3, 4))));

        Assertions.assertEquals(13.596, Double.parseDouble(toThreeDecimal.format(result.get(4, 0))));
        Assertions.assertEquals(11.462, Double.parseDouble(toThreeDecimal.format(result.get(4, 1))));
        Assertions.assertEquals(9.328, Double.parseDouble(toThreeDecimal.format(result.get(4, 2))));
        Assertions.assertEquals(7.194, Double.parseDouble(toThreeDecimal.format(result.get(4, 3))));
        Assertions.assertEquals(5.162, Double.parseDouble(toThreeDecimal.format(result.get(4, 4))));
    }

    @Test
    void matMul_WrongDimensions_ThrowsDimensionException() {

        leftMatrix = new MatJv(2, 2);
        rightMatrix = new MatJv(5, 2);

        Assertions.assertThrows(DimensionException.class, () -> axisNumJv.matMul(leftMatrix, rightMatrix));
    }

    @Test
    void builder_CannotCreateBuilder_ThrowsCreationalException() {

        Assertions.assertThrows(CreationalException.class, () -> new AxisNumJv.Builder().build());
    }

    @Test
    void builder_createsInstance_OperationWorks() {

        var newInstance = new AxisNumJv.Builder()
                .operationsImpl(new AxisRowOperation())
                .matrixProductImpl(new AxisColumnMatrixProduct())
                .build();

        leftMatrix = new MatJv(3, 3);
        rightMatrix = new MatJv(3, 3);

        leftMatrix.set(0, 0, 5.0);
        leftMatrix.set(0, 1, 8.0);
        leftMatrix.set(0, 2, 1.0);
        leftMatrix.set(1, 0, 7.0);
        leftMatrix.set(1, 1, 1.0);
        leftMatrix.set(1, 2, 5.0);
        leftMatrix.set(2, 0, 3.0);
        leftMatrix.set(2, 1, 2.0);
        leftMatrix.set(2, 2, 0.0);

        rightMatrix.set(0, 0, 7.0);
        rightMatrix.set(0, 1, 5.0);
        rightMatrix.set(0, 2, 1.0);
        rightMatrix.set(1, 0, 2.0);
        rightMatrix.set(1, 1, 4.0);
        rightMatrix.set(1, 2, 1.0);
        rightMatrix.set(2, 0, 1.0);
        rightMatrix.set(2, 1, 1.0);
        rightMatrix.set(2, 2, 1.0);

        var result = newInstance.add(leftMatrix, rightMatrix);

        Assertions.assertEquals(12.0, Double.parseDouble(toOneDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(13.0, Double.parseDouble(toOneDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(2.0, Double.parseDouble(toOneDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(9.0, Double.parseDouble(toOneDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(5.0, Double.parseDouble(toOneDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(6.0, Double.parseDouble(toOneDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(4.0, Double.parseDouble(toOneDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(3.0, Double.parseDouble(toOneDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(1.0, Double.parseDouble(toOneDecimal.format(result.get(2, 2))));
    }

    @Test
    void builder_createsInstance_MatrixProductWorks() {

        var newInstance = new AxisNumJv.Builder()
                .operationsImpl(new AxisRowOperation())
                .matrixProductImpl(new AxisColumnMatrixProduct())
                .build();


        leftMatrix = new MatJv(5, 2);
        rightMatrix = new MatJv(2, 5);

        leftMatrix.set(0, 0, 1.2);
        leftMatrix.set(0, 1, 2.2);
        leftMatrix.set(1, 0, 3.2);
        leftMatrix.set(1, 1, 4.2);
        leftMatrix.set(2, 0, 5.2);
        leftMatrix.set(2, 1, 6.2);
        leftMatrix.set(3, 0, 7.2);
        leftMatrix.set(3, 1, 8.2);
        leftMatrix.set(4, 0, 9.2);
        leftMatrix.set(4, 1, 10.2);

        rightMatrix.set(0, 0, 0.99);
        rightMatrix.set(0, 1, 0.88);
        rightMatrix.set(0, 2, 0.77);
        rightMatrix.set(0, 3, 0.66);
        rightMatrix.set(0, 4, 0.55);
        rightMatrix.set(1, 0, 0.44);
        rightMatrix.set(1, 1, 0.33);
        rightMatrix.set(1, 2, 0.22);
        rightMatrix.set(1, 3, 0.11);
        rightMatrix.set(1, 4, 0.01);

        var result = newInstance.matMul(leftMatrix, rightMatrix);

        Assertions.assertEquals(2.156, Double.parseDouble(toThreeDecimal.format(result.get(0, 0))));
        Assertions.assertEquals(1.782, Double.parseDouble(toThreeDecimal.format(result.get(0, 1))));
        Assertions.assertEquals(1.408, Double.parseDouble(toThreeDecimal.format(result.get(0, 2))));
        Assertions.assertEquals(1.034, Double.parseDouble(toThreeDecimal.format(result.get(0, 3))));
        Assertions.assertEquals(0.682, Double.parseDouble(toThreeDecimal.format(result.get(0, 4))));

        Assertions.assertEquals(5.016, Double.parseDouble(toThreeDecimal.format(result.get(1, 0))));
        Assertions.assertEquals(4.202, Double.parseDouble(toThreeDecimal.format(result.get(1, 1))));
        Assertions.assertEquals(3.388, Double.parseDouble(toThreeDecimal.format(result.get(1, 2))));
        Assertions.assertEquals(2.574, Double.parseDouble(toThreeDecimal.format(result.get(1, 3))));
        Assertions.assertEquals(1.802, Double.parseDouble(toThreeDecimal.format(result.get(1, 4))));

        Assertions.assertEquals(7.876, Double.parseDouble(toThreeDecimal.format(result.get(2, 0))));
        Assertions.assertEquals(6.622, Double.parseDouble(toThreeDecimal.format(result.get(2, 1))));
        Assertions.assertEquals(5.368, Double.parseDouble(toThreeDecimal.format(result.get(2, 2))));
        Assertions.assertEquals(4.114, Double.parseDouble(toThreeDecimal.format(result.get(2, 3))));
        Assertions.assertEquals(2.922, Double.parseDouble(toThreeDecimal.format(result.get(2, 4))));

        Assertions.assertEquals(10.736, Double.parseDouble(toThreeDecimal.format(result.get(3, 0))));
        Assertions.assertEquals(9.042, Double.parseDouble(toThreeDecimal.format(result.get(3, 1))));
        Assertions.assertEquals(7.348, Double.parseDouble(toThreeDecimal.format(result.get(3, 2))));
        Assertions.assertEquals(5.654, Double.parseDouble(toThreeDecimal.format(result.get(3, 3))));
        Assertions.assertEquals(4.042, Double.parseDouble(toThreeDecimal.format(result.get(3, 4))));

        Assertions.assertEquals(13.596, Double.parseDouble(toThreeDecimal.format(result.get(4, 0))));
        Assertions.assertEquals(11.462, Double.parseDouble(toThreeDecimal.format(result.get(4, 1))));
        Assertions.assertEquals(9.328, Double.parseDouble(toThreeDecimal.format(result.get(4, 2))));
        Assertions.assertEquals(7.194, Double.parseDouble(toThreeDecimal.format(result.get(4, 3))));
        Assertions.assertEquals(5.162, Double.parseDouble(toThreeDecimal.format(result.get(4, 4))));
    }

}