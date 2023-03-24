

public class Polygon3D {

    public Vector3D node1;
    public Vector3D node2;
    public Vector3D node3;
    public Material material;

    Polygon3D(Vector3D node1, Vector3D node2, Vector3D node3) {
        this.node1 = node1;
        this.node2 = node2;
        this.node3 = node3;
    }

    Polygon3D(Vector3D node1, Vector3D node2, Vector3D node3, Material material) {
        this.node1 = node1;
        this.node2 = node2;
        this.node3 = node3;
        this.material = material;
    }

    public Pair<Boolean, Double> polygonIntersected(RayOfLight ray, double k) {
        Pair<Boolean, Double> pair = new Pair<>();

        Vector3D x1 = node2.sub(node1);
        Vector3D x2 = node3.sub(node1);
        Vector3D pVec = Vector3D.cross(ray.direction, x2);
        double det = Vector3D.dot(x1, pVec);
        if (det < 1e-8 && det > -1e-8) {
            return new Pair<>(false, k);
        }
        double intDet = 1 / det;
        Vector3D tVec = ray.origin.sub(node1);
        double u = Vector3D.dot(tVec, pVec) * intDet;
        if (u < 0 || u > 1) {
            return new Pair<>(false, k);
        }
        Vector3D qVec = Vector3D.cross(tVec, x1);
        double v = Vector3D.dot(ray.direction, qVec) * intDet;
        if (v < 0 || v + u > 1) {
            return new Pair<>(false, k);
        }
        return new Pair<>(k > 1e-8, Vector3D.dot(x2, qVec) * intDet);
    }

    public Vector3D getNormalObserver(Vector3D observer) {
        Vector3D normal = Vector3D.cross(node2.sub(node1), node3.sub(node1));
        normal = normal.normal();
        if (Vector3D.dot(observer.normal(), normal) < 0) {
            normal = Vector3D.cross(node3.sub(node1), node2.sub(node1)).normal();
        }
        return normal;
    }

    public Vector3D getNormal() {
        return Vector3D.cross(node2.sub(node1), node3.sub(node1)).normal();
    }

    @Override
    public String toString() {
        return "Polygon3D{" +
                "node1=" + node1 +
                ", node2=" + node2 +
                ", node3=" + node3 +
                ", material=" + material +
                '}';
    }
}
