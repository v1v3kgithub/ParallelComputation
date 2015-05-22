package computation.engine;

import computation.Calculator;
import computation.PairOfObjects;

import java.util.List;

/**
 * Created by vivek on 22/05/15.
 */
public class SerialComputationEngine<T> extends AbstractComputationEngine<T> {
    public SerialComputationEngine(Calculator<T> calculator) {
        super(calculator);
    }

    @Override
    public String toString() {
        return "SerialComputationEngine";
    }

    @Override
    public void compute(List<T> objects, double deltaT, int numberOfSteps) {
        List<PairOfObjects<T>> pairOfObjects = calculator.createCombinationOfObjects(objects);
        for (int i = 0; i < numberOfSteps; i++) {
            for (PairOfObjects<T> objectPair : pairOfObjects) {
                calculator.taskToBePerformedOnPairOfObjects(objectPair);
            }
            calculator.taskToBePerformedWhenTheBarrierIsReached(objects,deltaT);
        }
    }
}
