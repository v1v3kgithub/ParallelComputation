package computation.engine;

import computation.Calculator;

import java.util.List;

/**
 * Created by vivek on 22/05/15.
 */
public class ParallelComputationEngineWithBarrier<T> extends AbstractComputationEngine<T>{
    public static final int DEFAULT_MIN_NUMER_OF_OBJECT_PER_THREAD = 4;
    private final int minNumberOfObjectPairPerThread;

    public ParallelComputationEngineWithBarrier(Calculator<T> calculator, int minNumberOfObjectPairPerThread) {
        super(calculator);
        this.minNumberOfObjectPairPerThread = minNumberOfObjectPairPerThread;
    }

    public ParallelComputationEngineWithBarrier(Calculator<T> calculator) {
        this(calculator, DEFAULT_MIN_NUMER_OF_OBJECT_PER_THREAD);
    }

    @Override
    public String toString() {
        return "ParallelComputationEngineWithBarrier";
    }

    @Override
    public void compute(List<T> objects, double deltaT, int numberOfSteps) {
        //TODO
    }
}
