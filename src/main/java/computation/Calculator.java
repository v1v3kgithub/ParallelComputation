package computation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivek on 22/05/15.
 */
public abstract class Calculator<T> {
    abstract public void taskToBePerformedOnPairOfObjects(PairOfObjects<T> pairOfObjects);

    abstract public void taskToBePerformedWhenTheBarrierIsReached(Iterable<T> allObjects, double deltaT);

    public List<PairOfObjects<T>> createCombinationOfObjects(List<T> allObjects) {
        List<PairOfObjects<T>> pairOfObjects = new ArrayList<PairOfObjects<T>>();
        for (int i = 0; i < allObjects.size(); i++) {
            T obj1 = allObjects.get(i);
            for (int j = i + 1; j < allObjects.size(); j++) {
                T obj2 = allObjects.get(j);
                pairOfObjects.add(new PairOfObjects<T>(obj1,obj2));
            }
        }
        return pairOfObjects;
    }
}
