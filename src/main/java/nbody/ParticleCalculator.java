package nbody;

import computation.Calculator;
import computation.PairOfObjects;

import javax.vecmath.Vector3d;

/**
 * Created by vivek on 22/05/15.
 */
public class ParticleCalculator extends Calculator<Particle> {
    public static final double G = 6.67259E-11;

    @Override
    public void taskToBePerformedOnPairOfObjects(PairOfObjects<Particle> pairOfObjects) {
        Particle p1 = pairOfObjects.getObject1();
        Particle p2 = pairOfObjects.getObject2();
        Vector3d distVector = new Vector3d();
        distVector.sub(p1.getLocation(),p2.getLocation());
        double r = distVector.length();
        double force = (p1.getMass()/r) * G * (p2.getMass()/r);
        double ratio = force/r;
        final Vector3d forceOfP1 = new Vector3d(distVector);
        forceOfP1.scale(ratio);
        p1.addForce(forceOfP1);
        forceOfP1.negate();
        p2.addForce(forceOfP1);
    }

    @Override
    public void taskToBePerformedWhenTheBarrierIsReached(Iterable<Particle> allObjects, double deltaT) {
        for (Particle object : allObjects) {
            object.updateVelocityAndPosition(deltaT);
        }
    }
}
