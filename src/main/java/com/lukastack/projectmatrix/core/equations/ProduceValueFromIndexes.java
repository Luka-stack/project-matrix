package com.lukastack.projectmatrix.core.equations;

@FunctionalInterface
public interface ProduceValueFromIndexes {

    double produce(int row, int col);
}
