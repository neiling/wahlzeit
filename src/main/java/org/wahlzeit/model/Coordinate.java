package org.wahlzeit.model;

/**
 * A coordinate to a location.
 * The class is immutable, otherwise it would be possible to change data without
 * saving it to the database. Alternatively it would be possible to extend the
 * class with DataObject. Read the report for more information.
 */
public class Coordinate {

    private final static double EPSILON = 1e-6; // 0.00001d

    private final double x;
    private final double y;
    private final double z;

    /**
     * @methodtype constructor
     */
    Coordinate(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getDistance(final Coordinate coor) {
        return Math.sqrt(
                squareDiff(coor.getX(), x) +
                squareDiff(coor.getY(), y) +
                squareDiff(coor.getZ(), z)
                );
    }

    private static double squareDiff(final double a, final double b) {
        return Math.pow(a - b, 2);
    }

    private static boolean isDoubleEqual(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }

    public boolean isEqual(final Coordinate that) {
        if (that == null)
            return false;
        if (that == this)
            return true;
        return isDoubleEqual(that.getX(), x)&&
            isDoubleEqual(that.getY(), y) &&
            isDoubleEqual(that.getZ(), z);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Coordinate ? isEqual((Coordinate) o) : false;
    }

    /**
     * @methodtype get
     */
    public double getX() {
        return x;
    }

    /**
     * @methodtype get
     */
    public double getY() {
        return y;
    }

    /**
     * @methodtype get
     */
    public double getZ() {
        return z;
    }

}
