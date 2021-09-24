package com.lukastack.projectmatrix.core;

import com.lukastack.projectmatrix.core.parallel.individual.ParallelElementWiseMatrixEquation;
import com.lukastack.projectmatrix.core.serial.SerialElementWiseMatrixEquation;

public interface ElementWiseMatrixEquation extends ParallelElementWiseMatrixEquation,
                                                   SerialElementWiseMatrixEquation {
}
