package computation.engine;

import computation.Calculator;

import java.util.List;

/**
 * Created by vivek on 22/05/15.
 */
public abstract class AbstractComputationEngine<T> {
    protected final Calculator<T> calculator;

    public AbstractComputationEngine(Calculator<T> calculator) {
        this.calculator = calculator;
    }

    public abstract void compute(List<T> objects, double deltaT, int numberOfSteps);
}
