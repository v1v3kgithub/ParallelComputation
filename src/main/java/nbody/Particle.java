package nbody;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * Created by vivek on 22/05/15.
 */
public class Particle {
    private final String id;
    private final double mass;
    private final Point3d location;
    private final Vector3d velocity;
    private Vector3d force;

    private Particle(String id,
                     double mass,
                     Point3d location,
                     Vector3d velocity,
                     Vector3d force) {
        this.force = force;
        this.velocity = velocity;
        this.location = location;
        this.mass = mass;
        this.id = id;
    }

    public Particle(String id, double mass, Point3d location, Vector3d velocity) {
        this(id, mass, location, velocity, new Vector3d());
    }

    public Particle copy() {
        return new Particle(id, mass, new Point3d(location),
                new Vector3d(velocity), new Vector3d(force));
    }

    public synchronized void addForce(Vector3d forceToAdd) {
        force.add(forceToAdd);
    }

    public synchronized void updateVelocityAndPosition(double deltaT) {
        Vector3d thisAcc = new Vector3d(force);
        thisAcc.scale(1f/mass);
        Vector3d deltaV = new Vector3d(thisAcc);
        deltaV.scale(deltaT);
        Vector3d averageV = new Vector3d(deltaV);
        this.velocity.add(deltaV);
        averageV.scaleAdd(-0.5, velocity);
        Point3d deltaPos = new Point3d(averageV);
        deltaPos.scale(deltaT);
        this.location.add(deltaPos);
        // Reset the force
        force.set(0,0,0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Particle particle = (Particle) o;

        if (Double.compare(particle.mass, mass) != 0) return false;
        if (!id.equals(particle.id)) return false;
        if (!location.equals(particle.location)) return false;
        if (!velocity.equals(particle.velocity)) return false;
        return force.equals(particle.force);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        temp = Double.doubleToLongBits(mass);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + location.hashCode();
        result = 31 * result + velocity.hashCode();
        result = 31 * result + force.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "id='" + id + '\'' +
                ", mass=" + mass +
                ", location=" + location +
                ", velocity=" + velocity +
                ", force=" + force +
                '}';
    }

    public String getId() {
        return id;
    }

    public double getMass() {
        return mass;
    }

    public Point3d getLocation() {
        return location;
    }

    public Vector3d getVelocity() {
        return velocity;
    }

    public Vector3d getForce() {
        return force;
    }
}
