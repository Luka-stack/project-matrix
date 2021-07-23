package com.lukastack.projectmatrix.core.operations;

import com.lukastack.projectmatrix.matrices.MatJv;

public interface IMatrixProduct {

    MatJv matMul(MatJv matLeft, MatJv matRight);
}
