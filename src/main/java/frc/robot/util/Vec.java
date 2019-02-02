package frc.robot.util;

public class Vec {

    public static final Vec ONES = new Vec(1, 1, 1);
    public static final Vec ZEROS = new Vec(0, 0, 0);

    private double x, y, z;

    public Vec(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec(Vec other) {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    public Vec() {
        this(0, 0, 0);
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(double val) {
        x = val;
    }

    public void setY(double val) {
        y = val;
    }

    public void setZ(double val) {
        z = val;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double dot(Vec other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public Vec add(Vec other) {
        x += other.x;
        y += other.y;
        z += other.z;
        return this;
    }

    public Vec hadamardProduct(Vec other) {
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
        return this;
    }

    public static Vec hadamardProduct(Vec a, Vec other) {
        return new Vec(a.x * other.x,
                a.y * other.y,
                a.z * other.z);
    }

    public static Vec add(Vec a, Vec b) {
        return new Vec(
                a.x + b.x,
                a.y + b.y,
                a.z + b.z
        );
    }

    public Vec subtract(Vec other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
        return this;
    }

    public static Vec subtract(Vec a, Vec b) {
        return new Vec(
                a.x - b.x,
                a.y - b.y,
                a.z - b.z
        );
    }

    public Vec multiply(double scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    public Vec divide(double scalar) {
        return multiply(1.0 / scalar);
    }

    public double magsq() {
        return x * x + y * y + z * z;
    }

    public double mag() {
        return Math.sqrt(magsq());
    }

    public Vec norm() {
        return divide(mag());
    }

    public Vec clone() {
        return new Vec(x, y, z);
    }
}
