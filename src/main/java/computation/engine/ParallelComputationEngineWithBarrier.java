package computation.engine;

import computation.Calculator;
import computation.PairOfObjects;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

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
    public void compute(final List<T> objects, final double deltaT, int numberOfSteps) {
        final CountDownLatch countDownLatch = new CountDownLatch(numberOfSteps);
        final int numberOfParallelWorkers = Runtime.getRuntime().availableProcessors();

        final CyclicBarrier barrier = new CyclicBarrier(numberOfParallelWorkers,
                // Runnable task to be executed every time the barrier is reached/
                // Barrier is reached?
                // All the subsets of objectpairs have been processed by each thread.
                new Runnable() {
                    @Override
                    public void run() {
                        calculator.taskToBePerformedWhenTheBarrierIsReached(objects,deltaT);
                        countDownLatch.countDown();
                    }
                });

        final Runnable[] taskPerformedOnEachObjectPairs = new Runnable[numberOfParallelWorkers];
        final List<PairOfObjects<T>> pairOfObjects = calculator.createCombinationOfObjects(objects);
        final int numberOfObjectPairs = pairOfObjects.size();
        int objectPairsPerThread = numberOfObjectPairs/numberOfParallelWorkers;

        if (objectPairsPerThread < minNumberOfObjectPairPerThread) {
            // If there are very few object pairs it is faster to use a serial processor.
            //
            throw new IllegalArgumentException("Very few particle pairs " + numberOfObjectPairs +
            " for the number of threads on this machine ");

        }
        final int fromIndex = 0;
        for (int i = 0; i < numberOfParallelWorkers; i++) {
            final int toIndex = fromIndex + objectPairsPerThread;
            final List<PairOfObjects<T>> subSetOfObjectPairs =
                    pairOfObjects.subList(fromIndex,
                            (toIndex > numberOfObjectPairs) ? numberOfObjectPairs: toIndex);

            // Create a runnable task for a subset of object pairs
            taskPerformedOnEachObjectPairs[i] = new Runnable() {
                @Override
                public void run() {
                    // Perform the task on each of the object pairs for this subset
                    while(countDownLatch.getCount() >0) {
                        // Process all the pair of particles
                        for (PairOfObjects<T> pairOfObject : subSetOfObjectPairs) {
                            calculator.taskToBePerformedOnPairOfObjects(pairOfObject);
                        }
                        try {
                            barrier.await();
                        } catch (InterruptedException ex) {
                            return;
                        } catch (BrokenBarrierException e) {
                            return;
                        }
                    }
                }
            };
        }

        // Start each worker
        for (Runnable taskPerformedOnEachObjectPair : taskPerformedOnEachObjectPairs) {
            new Thread(taskPerformedOnEachObjectPair).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {

        }

    }


}
