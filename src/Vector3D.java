public class Vector3D {
    public double x;
    public double y;
    public double z;

    public Vector3D() {
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(Vector3D vector3D) {
        if (vector3D != this) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public Vector3D sum(Vector3D vector) {
        return new Vector3D(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    public Vector3D sub(Vector3D vector) {
        return new Vector3D(this.x - vector.x, this.y - vector.y, this.z - vector.z);
    }

    public Vector3D mult(double multipler) {
        return new Vector3D(this.x * multipler, this.y * multipler, this.z * multipler);
    }

    public Vector3D div(double divisor) {
        return new Vector3D(this.x / divisor, this.y / divisor, this.z / divisor);
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vector3D normal() {
        double length = this.length();
        if (length == 0) {
            System.out.println("0 length while normalizin");
            return new Vector3D(0, 0, 0);
        }
        return new Vector3D(this.x / length, this.y / length, this.z / length);
    }

    public static Vector3D cross(Vector3D vector1, Vector3D vector2) {
        return new Vector3D(vector1.y * vector2.z - vector1.z * vector2.y,
                vector1.z * vector2.x - vector1.x * vector2.z,
                vector1.x * vector2.y - vector1.y * vector2.x);
    }

    public static double dot(Vector3D vector1, Vector3D vector2) {
        double x = vector1.x * vector2.x + vector1.y * vector2.y + vector1.z * vector2.z;
        if (Double.isNaN(x)) {
            System.out.println("Karaul");
        }
        return (vector1.x * vector2.x + vector1.y * vector2.y + vector1.z * vector2.z);
    }

    public static Vector3D repulse(Vector3D vector1, Vector3D polygonNormal) {
        return vector1.sub(polygonNormal.mult((2 * (dot(vector1, polygonNormal)))));
    }

    @Override
    public String toString() {
        return "Vector3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
